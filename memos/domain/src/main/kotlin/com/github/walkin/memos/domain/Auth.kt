package com.github.walkin.memos.domain

import com.github.walkin.usecase.Command

data class SignInRequest(val username: String, val password: String, val neverExpire: Boolean) :
  Command<String>()

data class SignupRequest(val username: String, val password: String) : Command<User>()
