package com.github.walkin.security.autoconfigure

import com.github.walkin.security.HttpExceptionFactory.badRequest
import com.github.walkin.security.JwtTokenReactFilter
import com.github.walkin.security.LoginCheckSuccessHandler
import com.github.walkin.security.LoginRequest
import com.github.walkin.security.SecurityJwtService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.ResolvableType
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
  companion object {
    val EXCLUDED_PATHS =
      arrayOf(
        "/graphql",
        "/api/*/login",
        "/webjars/**",
        "/swagger-ui.html",
        "/v3/api-docs/swagger-config",
        "v3/api-docs",
        "/api/*/auth/signup",
        "/api/*/workspace/profile",
        "/api/*/workspace/GENERAL",
        "/api/*/workspace/MEMO_RELATED",
        "/api/*/auth/refresh_token",
      )
  }

  @Bean
  fun springSecurityFilterChain(
    http: ServerHttpSecurity,
    jwtTokenReactFilter: JwtTokenReactFilter,
    jwtAuthenticationFilter: AuthenticationWebFilter,
  ): SecurityWebFilterChain {
    return http {
      authorizeExchange {
        authorize(pathMatchers(*EXCLUDED_PATHS), permitAll)
        authorize(anyExchange, authenticated)
      }
      cors { disable() }
      csrf { disable() }
      httpBasic { authenticationEntryPoint = HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED) }
      addFilterAt(jwtTokenReactFilter, SecurityWebFiltersOrder.AUTHORIZATION)
      addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
    }
  }

  @Bean
  fun reactiveAuthenticationManager(
    userDetailsService: ReactiveUserDetailsService
  ): ReactiveAuthenticationManager =
    UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService).apply {
      setPasswordEncoder(passwordEncoder())
    }

  @Bean
  fun loginCheckSuccessHandler(
    jwtService: SecurityJwtService,
    kotlinSerializationJsonEncoder: KotlinSerializationJsonEncoder,
  ): LoginCheckSuccessHandler {
    return LoginCheckSuccessHandler(jwtService, kotlinSerializationJsonEncoder)
  }

  @Bean
  fun authenticationWebFilter(
    manager: ReactiveAuthenticationManager,
    jwtConverter: ServerAuthenticationConverter,
    securityJwtService: SecurityJwtService,
    loginCheckSuccessHandler: LoginCheckSuccessHandler,
  ): AuthenticationWebFilter =
    AuthenticationWebFilter(manager).apply {
      // 匹配登录地址
      setRequiresAuthenticationMatcher { pathMatchers(POST, "/api/v1/login").matches(it) }
      // 登录请求的负载转成Authentication对象
      setServerAuthenticationConverter(jwtConverter)

      // 登录验证成功后处理
      setAuthenticationSuccessHandler(loginCheckSuccessHandler)

      setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance())
    }

  @Bean
  fun jwtConverter(jsonDecoder: KotlinSerializationJsonDecoder): ServerAuthenticationConverter {

    return object : ServerAuthenticationConverter {
      override fun convert(exchange: ServerWebExchange): Mono<Authentication> = mono {
        val loginRequest: LoginRequest = getUsernameAndPassword(exchange) ?: throw badRequest()

        UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
      }

      private suspend fun getUsernameAndPassword(exchange: ServerWebExchange): LoginRequest? {
        val dataBuffer = exchange.request.body
        val type = ResolvableType.forClass(LoginRequest::class.java)
        return jsonDecoder
          .decodeToMono(dataBuffer, type, APPLICATION_JSON, mapOf())
          .onErrorResume { Mono.empty<LoginRequest>() }
          .cast(LoginRequest::class.java)
          .awaitFirstOrNull()
      }
    }
  }

  @Bean fun securityFilter(jwtService: SecurityJwtService) = JwtTokenReactFilter(jwtService)

  @Bean fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
