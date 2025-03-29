package com.github.walkin.security

import kotlinx.serialization.Serializable

@Serializable data class LoginRequest(val username: String, val password: String)
