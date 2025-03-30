package com.github.walkin.memos.domain

import com.github.walkin.memos.entity.User
import com.github.walkin.usecase.Command
import kotlin.time.DurationUnit
import kotlin.time.toDuration

val accessTokenDuration = (7 * 24).toDuration(DurationUnit.HOURS)

data class SignIn(val username: String, val password: String, val neverExpire: Boolean = false) :
  Command<String>()

data class SignUp(val username: String, val password: String) : Command<User>()
