package model.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthTokens(
    private val accessToken: String,
    private val refreshToken: String,
)
