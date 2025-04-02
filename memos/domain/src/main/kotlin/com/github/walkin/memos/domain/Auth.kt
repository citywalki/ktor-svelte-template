package com.github.walkin.memos.domain

import com.github.walkin.security.JwtTokens
import com.github.walkin.usecase.Command
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlinx.serialization.Serializable

val accessTokenDuration = (7 * 24).toDuration(DurationUnit.HOURS)

@Serializable
data class SignIn(val username: String, val password: String, val neverExpire: Boolean = false) :
  Command<JwtTokens>()

@Serializable data class SignUp(val username: String, val password: String) : Command<UserId>()
