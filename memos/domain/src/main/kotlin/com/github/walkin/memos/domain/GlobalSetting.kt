package com.github.walkin.memos.domain

import com.github.walkin.usecase.Command
import java.time.LocalDateTime
import kotlinx.serialization.Serializable

enum class GlobalSettingKey(val id: Int) {
  WORKSPACE_SETTING_KEY_UNSPECIFIED(0),

  // 基本设置
  BASIC(1),

  // 一般设置
  GENERAL(2),

  // 存储设置
  STORAGE(3),

  // 备忘录相关
  MEMO_RELATED(4),
}

@Serializable
sealed class GlobalSetting {
  abstract val key: GlobalSettingKey
}

@Serializable
data class GlobalGeneralSetting(
  // disallow_user_registration disallows user registration.
  var disallowUserRegistration: Boolean = false,

  // disallow_password_auth disallows password authentication.
  var disallowPasswordAuth: Boolean = false,

  // additional_script is the additional script.
  val additionalScript: String? = "",

  // additional_style is the additional style.
  val additionalStyle: String? = "",

  // custom_profile is the custom profile.
  val customProfile: GlobalCustomProfile = GlobalCustomProfile(),

  // week_start_day_offset is the week start day offset from Sunday.
  // 0: Sunday, 1: Monday, 2: Tuesday, 3: Wednesday, 4: Thursday, 5: Friday, 6: Saturday
  // Default is Sunday.
  val weekStartDayOffset: Int = 0,

  // disallow_change_username disallows changing username.
  var disallowChangeUsername: Boolean = false,

  // disallow_change_nickname disallows changing nickname.
  var disallowChangeNickname: Boolean = false,
) : GlobalSetting() {
  override val key: GlobalSettingKey
    get() = GlobalSettingKey.GENERAL

  @Serializable
  data class GlobalCustomProfile(
    val title: String? = null,
    val description: String? = null,
    val logoUrl: String? = null,
    val locale: String? = null,
    val appearance: String? = null,
  )
}

@Serializable
data class GlobalBasicSetting(
  // The secret key for workspace. Mainly used for session management.
  var secretKey: String? = null,
  // The current schema version of database.
  var schemaVersion: String? = null,
) : GlobalSetting() {
  override val key: GlobalSettingKey
    get() = GlobalSettingKey.BASIC
}

@Serializable
data class GlobalStorageSetting(val aaa: String? = null) : GlobalSetting() {
  override val key: GlobalSettingKey
    get() = GlobalSettingKey.STORAGE
}

@Serializable
data class GlobalMemoRelatedSetting(
  // disallow_public_visibility disallows set memo as public visibility.
  var disallowPublicVisibility: Boolean = false,
  // display_with_update_time orders and displays memo with update time.
  var displayWithUpdateTime: Boolean = false,
  // content_length_limit is the limit of content length. Unit is byte.
  var contentLengthLimit: Int = 8 * 1024,
  // enable_auto_compact enables auto compact for large content.
  var enableAutoCompact: Boolean = false,
  // enable_double_click_edit enables editing on double click.
  var enableDoubleClickEdit: Boolean = false,
  // enable_link_preview enables links preview.
  var enableLinkPreview: Boolean = false,
  // enable_comment enables comment.
  var enableComment: Boolean = false,
  // enable_location enables setting location for memo.
  var enableLocation: Boolean = false,
  // default_visibility set the global memos default visibility.
  var defaultVisibility: String? = null,
  // reactions is the list of reactions.
  var reactions: List<String> = emptyList(),
  // disable markdown shortcuts
  var disableMarkdownShortcuts: Boolean = false,
) : GlobalSetting() {
  override val key: GlobalSettingKey
    get() = GlobalSettingKey.MEMO_RELATED
}

data class GlobalSettingEntity(
  val version: Int,
  val createdAt: LocalDateTime = LocalDateTime.MIN,
  val updatedAt: LocalDateTime = LocalDateTime.MIN,
  val name: GlobalSettingKey = GlobalSettingKey.WORKSPACE_SETTING_KEY_UNSPECIFIED,
  var value: String,
)

@Serializable
data class SetGlobalSetting(val globalSetting: GlobalSetting) : Command<GlobalSetting>()

@Serializable
data class UpdateWorkspaceSettingRequest(
  val generalSetting: GlobalGeneralSetting? = null,
  val storageSetting: GlobalStorageSetting? = null,
  val memoRelatedSetting: GlobalMemoRelatedSetting? = null,
)

@Serializable
data class GlobalProfile(
  // The name of instance owner.
  // Format: users/{user}
  val owner: String,
  // version is the current version of instance
  val version: String,
  // mode is the instance mode (e.g. "prod", "dev" or "demo").
  val mode: String,
  val instanceUrl: String,
)

@Serializable
data class WorkspaceSettingResponse(
  var name: String,
  var generalSetting: GlobalGeneralSetting? = null,
  var storageSetting: GlobalStorageSetting? = null,
  var memoRelatedSetting: GlobalMemoRelatedSetting? = null,
)
