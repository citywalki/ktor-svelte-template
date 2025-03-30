package com.github.walkin.memos.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.github.walkin.security.JWTPayload
import com.github.walkin.security.SecurityJwtService
import java.util.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SecurityJwtServiceImpl(
  @Value("\${app.secret}") val secret: String,
  @Value("\${app.refresh}") val refresh: String,
) : SecurityJwtService {
  fun decodeAccessToken(accessToken: String): DecodedJWT = decode(secret, accessToken)

  override fun decodeRefreshToken(refreshToken: String): JWTPayload {
    val decodedJWT = decode(refresh, refreshToken)
    return JWTPayload(
      username = decodedJWT.subject,
      role = decodedJWT.getClaim("role").asList(String::class.java),
    )
  }

  override fun getUsername(token: String): String = decodeAccessToken(token).subject

  fun getRoles(decodedJWT: DecodedJWT): MutableList<String> =
    decodedJWT.getClaim("role").asList(String::class.java)

  override fun getRoles(token: String) = getRoles(decodeAccessToken(token))

  override fun accessToken(username: String, roles: List<String>): String =
    generate(username, FIFTEEN_MIN, roles, secret)

  override fun refreshToken(username: String, roles: List<String>): String =
    generate(username, FOUR_HOURS, roles, refresh)

  private fun generate(
    username: String,
    expirationInMillis: Int,
    roles: List<String>,
    signature: String,
  ): String =
    JWT.create()
      .withSubject(username)
      .withExpiresAt(Date(System.currentTimeMillis() + expirationInMillis))
      .withArrayClaim("role", roles.toTypedArray())
      .sign(Algorithm.HMAC512(signature.toByteArray()))

  private fun decode(signature: String, token: String): DecodedJWT =
    JWT.require(Algorithm.HMAC512(signature.toByteArray()))
      .build()
      .verify(token.replace("Bearer ", ""))

  companion object {
    private const val FIFTEEN_MIN = 1000 * 60 * 15
    private const val FOUR_HOURS = 1000 * 60 * 60 * 4
  }
}
