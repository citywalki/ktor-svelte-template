package com.github.walkin.memos.entity

import java.time.LocalDateTime
import kotlinx.serialization.Serializable

enum class WorkspaceSettingKey(val id: Int) {
  WORKSPACE_SETTING_KEY_UNSPECIFIED(0),

  // BASIC is the key for basic settings.
  BASIC(1),

  // GENERAL is the key for general settings.
  GENERAL(2),

  // STORAGE is the key for storage settings.
  STORAGE(3),

  // MEMO_RELATED is the key for memo related settings.
  MEMO_RELATED(4),
}

@Serializable
sealed class WorkspaceSetting {
  abstract val key: WorkspaceSettingKey
}

/**
 * @param disallowUserRegistration disallow_user_registration disallows user registration.
 * @param disallowPasswordAuth disallow_password_auth disallows password authentication.
 * @param additionalScript additional_script is the additional script.
 * @param additionalStyle additional_style is the additional style.
 * @param customProfile
 * @param weekStartDayOffset week_start_day_offset is the week start day offset from Sunday. 0:
 *   Sunday, 1: Monday, 2: Tuesday, 3: Wednesday, 4: Thursday, 5: Friday, 6: Saturday Default is
 *   Sunday.
 * @param disallowChangeUsername disallow_change_username disallows changing username.
 * @param disallowChangeNickname disallow_change_nickname disallows changing nickname.
 */
@Serializable
data class WorkspaceGeneralSetting(
  // disallow_user_registration disallows user registration.
  var disallowUserRegistration: Boolean = false,

  // disallow_password_auth disallows password authentication.
  var disallowPasswordAuth: Boolean = false,

  // additional_script is the additional script.
  val additionalScript: String? = "",

  // additional_style is the additional style.
  val additionalStyle: String? = "",

  // custom_profile is the custom profile.
  val customProfile: WorkspaceCustomProfile? = null,

  // week_start_day_offset is the week start day offset from Sunday.
  // 0: Sunday, 1: Monday, 2: Tuesday, 3: Wednesday, 4: Thursday, 5: Friday, 6: Saturday
  // Default is Sunday.
  val weekStartDayOffset: Int = 0,

  // disallow_change_username disallows changing username.
  var disallowChangeUsername: Boolean = false,

  // disallow_change_nickname disallows changing nickname.
  var disallowChangeNickname: Boolean = false,
) : WorkspaceSetting() {
  override val key: WorkspaceSettingKey
    get() = WorkspaceSettingKey.GENERAL

  @Serializable
  data class WorkspaceCustomProfile(
    val title: String,
    val description: String,
    val logoUrl: String,
    val locale: String,
    val appearance: String,
  )
}

@Serializable
data class WorkspaceBasicSetting(
  // The secret key for workspace. Mainly used for session management.
  var secretKey: String? = null,
  // The current schema version of database.
  var schemaVersion: String? = null,
) : WorkspaceSetting() {
  override val key: WorkspaceSettingKey
    get() = WorkspaceSettingKey.BASIC
}

@Serializable
data class WorkspaceStorageSetting(val aaa: String? = null) : WorkspaceSetting() {
  override val key: WorkspaceSettingKey
    get() = WorkspaceSettingKey.STORAGE
}

@Serializable
data class WorkspaceMemoRelatedSetting(
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
) : WorkspaceSetting() {
  override val key: WorkspaceSettingKey
    get() = WorkspaceSettingKey.MEMO_RELATED
}

data class WorkspaceSettingEntity(
  val version: Int,
  val createdAt: LocalDateTime = LocalDateTime.MIN,
  val updatedAt: LocalDateTime = LocalDateTime.MIN,
  val name: WorkspaceSettingKey = WorkspaceSettingKey.WORKSPACE_SETTING_KEY_UNSPECIFIED,
  var value: String,
)
