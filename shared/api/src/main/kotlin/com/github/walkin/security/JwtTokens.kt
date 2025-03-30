package com.github.walkin.security

import kotlinx.serialization.Serializable

@Serializable
data class JwtTokens(val accessToken: String? = null, val refreshToken: String? = null)

@Serializable data class JWTPayload(val username: String, val role: List<String>)
