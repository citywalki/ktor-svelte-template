package com.github.walkin.memos.entity

import com.github.walkin.memos.domain.MemosVisibility
import com.github.walkin.shared.entity.RowStatus
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.komapper.annotation.*

@Serializable
enum class UserRole {
  ROLE_UNSPECIFIED,
  HOST,
  ADMIN,
  USER,
}

@Serializable
@KomapperEntity
@KomapperTable(name = "memos_users")
data class User(
  @KomapperId val id: EntityID = 0,
  @KomapperVersion val version: Int = 0,
  @KomapperCreatedAt val createdAt: LocalDateTime? = null,
  @KomapperUpdatedAt val updatedAt: LocalDateTime? = null,
  val username: String,
  val password: String,
  var status: RowStatus = RowStatus.NORMAL,
  var role: UserRole = UserRole.USER,
  var email: String? = null,
  var nickname: String? = null,
  var avatarUrl: String? = null,
)

@KomapperEntity
@KomapperTable(name = "user_space")
data class UserSpace(
  @KomapperId @KomapperAutoIncrement val id: EntityID = 0,
  @KomapperVersion val version: Int = 0,
  @KomapperCreatedAt val createdAt: LocalDateTime? = null,
  @KomapperUpdatedAt val updatedAt: LocalDateTime? = null,
  val name: String,
  val userId: EntityID,
)

data class UserSetting(
  val id: EntityID,
  var locale: String = "en",
  var appearance: String = "system",
  var memoVisibility: MemosVisibility = MemosVisibility.PRIVATE,
)

@KomapperEntity
@KomapperTable(name = "user_setting")
data class UserSettingEntity(@KomapperEmbeddedId val unique: UserSettingUnique, val value: String)

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

enum class UserSettingKey {
  USER_SETTING_KEY_UNSPECIFIED,
  // Access tokens for the user.
  ACCESS_TOKENS,
  // The locale of the user.
  LOCALE,
  // The appearance of the user.
  APPEARANCE,
  // The visibility of the memo.
  MEMO_VISIBILITY,
  // The shortcuts of the user.
  SHORTCUTS,
}

data class UserSettingUnique(
  val userId: EntityID,
  @KomapperEnum(EnumType.NAME) val key: UserSettingKey,
)
