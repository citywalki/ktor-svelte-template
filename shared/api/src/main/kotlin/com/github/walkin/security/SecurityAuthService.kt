package com.github.walkin.security

interface SecurityAuthService {
  suspend fun findByUsername(username: String?): Account?
}
