package domain.user

import domain.TableId
import kotlinx.serialization.Serializable

@Serializable
data class UserSetting(
    val id: TableId,
    var locale: Locale = Locale.CN,
    var appearance: String = "system",
) {
    @Serializable
    enum class Locale {
        EN,
        CN,
    }
}
