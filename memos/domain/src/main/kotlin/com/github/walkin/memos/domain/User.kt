package com.github.walkin.memos.domain

import com.github.walkin.usecase.Command
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUser(val userId: String, val name: String, val password: String) : Command<Unit>()

@JvmInline @Serializable value class UserId(val value: TableId)

@Serializable data class User(val id: UserId)

@Serializable
data class UserSetting(
  val id: UserId,
  var locale: String = "en",
  var appearance: String = "system",
  var memoVisibility: MemosVisibility = MemosVisibility.PRIVATE,
)
