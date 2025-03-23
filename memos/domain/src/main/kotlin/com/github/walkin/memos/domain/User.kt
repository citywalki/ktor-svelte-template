package com.github.walkin.memos.domain

import com.github.walkin.shared.entity.RowStatus
import com.github.walkin.usecase.Command
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
enum class UserRole {
  ROLE_UNSPECIFIED,
  HOST,
  ADMIN,
  USER,
}

@Serializable
data class User(
  val id: Long = 0,
  val version: Int = 0,
  val createdAt: LocalDateTime? = null,
  val updatedAt: LocalDateTime? = null,
  val username: String,
  val password: String,
  var status: RowStatus = RowStatus.NORMAL,
  var role: UserRole = UserRole.USER,
  var email: String? = null,
  var nickname: String? = null,
  var avatarUrl: String? = null,
)

data class UserSetting(
  val name: Long,
  var locale: String = "en",
  var appearance: String = "system",
  var memoVisibility: MemosVisibility = MemosVisibility.PRIVATE,
)

@Serializable
data class CreateUser(
  val name: String,
  val role: UserRole,
  val username: String,
  val nickname: String,
  val email: String,
  val avatarUrl: String,
  val description: String,
  val state: RowStatus = RowStatus.NORMAL,
  val password: String,
) : Command<User>()

@Serializable
data class CreateUserAccessTokenRequest(val description: String, val expiresAt: LocalDateTime) :
  Command<String>()

@Serializable
data class UpdateUser(val userId: String, val name: String, val password: String) : Command<Unit>()

@Serializable
data class UserStats(
  val name: String,
  val memoDisplayTimestamps: List<LocalDateTime>,
  val memoTypeStats: MemoTypeStats,
  val tagCount: Map<String, Int>,
)

@Serializable
data class MemoTypeStats(
  val linkCount: Int,
  val codeCount: Int,
  val todoCount: Int,
  val undoCount: Int,
)
