package com.github.walkin.security

data class Account(val username: String, val password: String, val role: Set<String>) {}
