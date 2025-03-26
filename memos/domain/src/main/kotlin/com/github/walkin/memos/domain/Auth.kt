package com.github.walkin.memos.domain

import com.github.walkin.usecase.Command
import kotlin.time.DurationUnit
import kotlin.time.toDuration

val accessTokenDuration = (7 * 24).toDuration(DurationUnit.HOURS)

data class SignInRequest(
  val username: String,
  val password: String,
  val neverExpire: Boolean = false,
) : Command<String>()

data class SignupRequest(val username: String, val password: String) : Command<User>()
