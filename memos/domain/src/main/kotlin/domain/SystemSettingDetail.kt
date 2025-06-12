package domain

import kotlinx.serialization.Serializable

@Serializable
data class SystemSetting(
    val key: SystemSettingKey,

    val generalSetting: GeneralSystemSettingDetail? = null,
    val memoRelatedSetting: MemoRelatedSystemSettingDetail? = null
)

@Serializable
sealed class SystemSettingDetail

enum class SystemSettingKey(val id: Int) {
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
data class GeneralSystemSettingDetail(
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
) : SystemSettingDetail() {

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
data class BasicSystemSettingDetail(
    // The secret key for workspace. Mainly used for session management.
    var secretKey: String? = null,
    // The current schema version of database.
    var schemaVersion: String? = null,
) : SystemSettingDetail()

@Serializable
data class StorageSystemSettingDetail(val aaa: String? = null) : SystemSettingDetail()

@Serializable
data class MemoRelatedSystemSettingDetail(
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
) : SystemSettingDetail()

@Serializable
data class SystemProfile(
    // The name of instance owner.
    val owner: UserId? = null,
    // version is the current version of instance
    val version: String,
    // mode is the instance mode (e.g. "prod", "dev" or "demo").
    val mode: String,
    val instanceUrl: String,
)
