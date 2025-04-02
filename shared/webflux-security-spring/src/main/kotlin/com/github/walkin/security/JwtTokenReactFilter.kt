package com.github.walkin.security

import org.slf4j.kotlin.getLogger
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.util.context.Context

class JwtTokenReactFilter(private val jwtService: SecurityJwtService) : WebFilter {
  private val log by getLogger()

  override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
    val token = exchange.jwtAccessToken() ?: return chain.filter(exchange)
    try {
      val auth =
        UsernamePasswordAuthenticationToken(
          jwtService.getUsername(token),
          null,
          jwtService.getRoles(token).map { SimpleGrantedAuthority(it) },
        )
      val context: Context = ReactiveSecurityContextHolder.withAuthentication(auth)
      return chain.filter(exchange).contextWrite(context)
    } catch (e: Exception) {
      log.error("JWT exception", e)
    }
    return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.clearContext())
  }

  companion object {
    fun ServerWebExchange.jwtAccessToken(): String? =
      request.headers
        .getFirst(AUTHORIZATION)
        ?.let { it.ifEmpty { null } }
        ?.substringAfter("Bearer ")
  }
}
