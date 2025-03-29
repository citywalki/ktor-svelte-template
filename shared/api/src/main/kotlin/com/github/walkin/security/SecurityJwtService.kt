package com.github.walkin.security

interface SecurityJwtService {
  fun getUsername(jwtToken: String): String

  fun getRoles(token: String): List<String>

  fun accessToken(username: String, roles: List<String>): String

  fun refreshToken(username: String, roles: List<String>): String

  fun decodeRefreshToken(refreshToken: String): JWTPayload
}
