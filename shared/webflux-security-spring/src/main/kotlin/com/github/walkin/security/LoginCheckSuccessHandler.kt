package com.github.walkin.security

import com.github.walkin.security.HttpExceptionFactory.unauthorized
import org.springframework.core.ResolvableType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import reactor.core.publisher.Mono

class LoginCheckSuccessHandler(
  private val jwtService: SecurityJwtService,
  private val kotlinSerializationJsonEncoder: KotlinSerializationJsonEncoder,
) : ServerAuthenticationSuccessHandler {
  override fun onAuthenticationSuccess(
    webFilterExchange: WebFilterExchange,
    authentication: Authentication,
  ): Mono<Void> {
    val principal = authentication.principal ?: throw unauthorized()

    when (principal) {
      is User -> {
        val roles = principal.authorities.map { it.authority }

        val accessToken = jwtService.accessToken(principal.username, roles)
        val refreshToken = jwtService.refreshToken(principal.username, roles)

        val exchange = webFilterExchange.exchange ?: throw unauthorized()

        with(exchange.response.headers) {
          setBearerAuth(accessToken)
          set("Refresh-Token", refreshToken)
        }

        val response = JwtTokens(accessToken, refreshToken)
        val type = ResolvableType.forClass(JwtTokens::class.java)

        return exchange.response.writeWith(
          Mono.just(
            kotlinSerializationJsonEncoder.encodeValue(
              response,
              exchange.response.bufferFactory(),
              type,
              APPLICATION_JSON,
              mapOf(),
            )
          )
        )
      }

      else -> throw RuntimeException("Not User!") // TODO: separate exception
    }
  }
}
