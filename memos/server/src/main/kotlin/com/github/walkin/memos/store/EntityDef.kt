package com.github.walkin.memos.store

import com.github.walkin.memos.entity.*
import com.github.walkin.shared.entity.RowStatus
import kotlinx.datetime.LocalDateTime
import org.komapper.annotation.*
import org.komapper.core.type.ClobString

@KomapperEntityDef(Inbox::class)
@KomapperTable(name = "inbox")
private data class InboxDef(
  @KomapperId val id: Long? = null,
  @KomapperCreatedAt val createdTs: LocalDateTime,
  val senderId: Long? = null,
  val receiverId: Long? = null,
  @KomapperEnum(type = EnumType.NAME) val status: InboxStatus,
  val message: Nothing,
)

@KomapperEntityDef(Memo::class)
private data class MemoDef(
  @KomapperId val uid: Nothing,
  var rowStatus: Nothing,
  var creator: Nothing,
  @KomapperColumn(alternateType = ClobString::class) var content: String,
  var payload: Nothing,
  var visibility: Nothing,
  @KomapperCreatedAt val createdAt: Nothing?,
)

@KomapperEntityDef(User::class)
@KomapperTable(name = "memos_users")
private data class UserDef(
  @KomapperId @KomapperAutoIncrement val id: Long = 0,
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

@KomapperEntityDef(UserSpace::class)
@KomapperTable(name = "user_space")
private data class UserSpaceDef(
  @KomapperId @KomapperAutoIncrement val id: Long,
  @KomapperVersion val version: Int = 0,
  @KomapperCreatedAt val createdAt: LocalDateTime,
  @KomapperUpdatedAt val updatedAt: LocalDateTime,
  val name: String,
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
  val userId: Long,
  @KomapperEnum(EnumType.NAME) val key: UserSettingKey,
)

@KomapperEntity
@KomapperTable(name = "user_setting")
data class UserSetting(@KomapperEmbeddedId val unique: UserSettingUnique, val value: String)

@KomapperEntity
@KomapperTable("system_setting")
data class WorkspaceSetting(
  @KomapperVersion val version: Int = 0,
  @KomapperCreatedAt val createdAt: LocalDateTime,
  @KomapperUpdatedAt val updatedAt: LocalDateTime,
  @KomapperId val name: GlobalSettingKey = GlobalSettingKey.WORKSPACE_SETTING_KEY_UNSPECIFIED,
  var value: String,
)
