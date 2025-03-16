package com.github.walkin.security

interface JwtService {
  fun getUsername(jwtToken: String): String

  fun getRoles(token: String): List<String>

  fun accessToken(username: String, expirationInMillis: Int, roles: Array<String>): String

  fun refreshToken(username: String, expirationInMillis: Int, roles: Array<String>): String
}
