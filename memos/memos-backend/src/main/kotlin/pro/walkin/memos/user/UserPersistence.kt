package pro.walkin.memos.user

import domain.User
import domain.UserId
import domain.UserName
import domain.UserRole
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.komapper.annotation.KomapperCreatedAt
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperEntityDef
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable
import org.komapper.annotation.KomapperUpdatedAt
import org.komapper.annotation.KomapperVersion
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.operator.count
import org.komapper.core.dsl.query.map
import org.komapper.core.dsl.query.singleOrNull

fun UserId.Companion.create(): UserId {
    return UserId(1L)
}

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

@KomapperEntity(aliases = ["userSetting"])
@KomapperTable("user_setting")
data class UserSettingTable(
    @KomapperId val userId: UserId,
    val key: UserSettingKey,
    val value: String
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

object UserDAO {

    fun findUser(id: UserId) =
        QueryDsl.from(Meta.user).where {
            Meta.user.id eq id
        }.singleOrNull()

    fun findUser(username: UserName) =
        QueryDsl.from(Meta.user).where {
            Meta.user.username eq username
        }.singleOrNull()

    fun countUser(role: UserRole) =
        QueryDsl.from(Meta.user).where {
            Meta.user.role eq role
        }.select(count()).map { it ?: 0 }

    fun insertUser(user: User) = QueryDsl.insert(Meta.user).single(user)
}
