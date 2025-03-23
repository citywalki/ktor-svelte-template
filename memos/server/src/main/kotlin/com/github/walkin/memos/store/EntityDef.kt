package com.github.walkin.memos.store

import com.github.walkin.memos.domain.*
import com.github.walkin.shared.entity.RowStatus
import kotlinx.datetime.LocalDateTime
import org.komapper.annotation.*
import org.komapper.core.type.ClobString

@KomapperEntityDef(Inbox::class)
@KomapperTable(name = "inbox")
data class InboxDef(
  @KomapperId val id: Long? = null,
  @KomapperCreatedAt val createdTs: LocalDateTime,
  val senderId: Long? = null,
  val receiverId: Long? = null,
  @KomapperEnum(type = EnumType.NAME) val status: InboxStatus,
  val message: Nothing,
)

@KomapperEntityDef(Memo::class)
data class MemoDef(
  @KomapperId val uid: Nothing,
  var rowStatus: Nothing,
  var creator: Nothing,
  @KomapperColumn(alternateType = ClobString::class) var content: String,
  var payload: Nothing,
  var visibility: Nothing,
  @KomapperCreatedAt val createdAt: Nothing?,
)

@KomapperEntityDef(GlobalSettingEntity::class)
@KomapperTable("system_setting")
data class WorkspaceSettingDef(
  @KomapperVersion val version: Int = 0,
  @KomapperCreatedAt val createdAt: LocalDateTime,
  @KomapperUpdatedAt val updatedAt: LocalDateTime,
  @KomapperId val name: GlobalSettingKey = GlobalSettingKey.WORKSPACE_SETTING_KEY_UNSPECIFIED,
  var value: String,
)

@KomapperEntityDef(User::class)
@KomapperTable(name = "memos_users")
data class UserDef(
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
