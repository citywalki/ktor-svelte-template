package com.github.walkin.security

interface PasswordEncoder {
  fun encode(password: String): String
}
