package com.github.walkin.memos.entity

import com.github.walkin.shared.entity.RowStatus
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
  val id: EntityID = -1,
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

data class UserSpace(
  val id: EntityID? = null,
  val version: Int = 0,
  val createdAt: LocalDateTime? = null,
  val updatedAt: LocalDateTime? = null,
  val name: String,
  val userId: EntityID,
)

data class UserSetting(
  val id: EntityID,
  var locale: String = "en",
  var appearance: String = "system",
  var memoVisibility: MemosVisibility = MemosVisibility.PRIVATE,
)

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
