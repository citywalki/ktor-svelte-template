package app.backend.domain.user.persistence

import model.user.UserId
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable

@KomapperEntity(aliases = ["userSetting"])
@KomapperTable("user_setting")
data class UserSettingDef(
    @KomapperId val userId: UserId,
    val key: UserSettingKey,
    val value: String
) {
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
}
