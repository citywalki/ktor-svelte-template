package entity

import domain.RowStatus
import domain.User
import domain.UserRole
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.komapper.annotation.KomapperCreatedAt
import org.komapper.annotation.KomapperEntityDef
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable
import org.komapper.annotation.KomapperUpdatedAt
import org.komapper.annotation.KomapperVersion

@KomapperEntityDef(User::class)
@KomapperTable("memos_user")
data class UserDef(
  @KomapperId val id: Nothing,
  @KomapperVersion val version: Nothing,
  @KomapperCreatedAt val createdAt: Nothing,
  @KomapperUpdatedAt val updatedAt: Nothing,
  val username: Nothing,
  val hashedPassword: Nothing,
  val status: Nothing,
  val role: Nothing,
  val email: Nothing,
  val nickname: Nothing,
  val avatarUrl: Nothing,
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
