package com.github.walkin.security

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class JwtAuthFailureHandler : ServerAuthenticationFailureHandler {

  override fun onAuthenticationFailure(
    webFilterExchange: WebFilterExchange,
    exception: AuthenticationException,
  ): Mono<Void> = mono {
    val exchange = webFilterExchange.exchange ?: throw HttpExceptionFactory.unauthorized()

    with(exchange.response) {
      statusCode = HttpStatus.INTERNAL_SERVER_ERROR
      val bytes: ByteArray = (exception.message ?: "").toByteArray()
      val buffer: DataBuffer = exchange.response.bufferFactory().wrap(bytes)
      exchange.response.writeWith(Flux.just(buffer)).awaitFirstOrNull()
    }
  }
}
