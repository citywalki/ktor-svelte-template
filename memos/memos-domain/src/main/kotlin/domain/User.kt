package domain

import dev.whyoleg.cryptography.CryptographyProvider
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
enum class UserRole {
    ROLE_UNSPECIFIED,
    HOST,
    ADMIN,
    USER,
}

@Serializable
@JvmInline
value class UserId(val value: Long) {
    companion object
}

@Serializable
@JvmInline
value class UserName(val value: String) {
    companion object {
        fun from(username: String) = UserName(username)
    }
}

@Serializable
@JvmInline
value class HashedPassword(val value: String) {
    companion object {
        fun from(password: String): HashedPassword {
            val hashed = CryptographyProvider.Default
                .get(dev.whyoleg.cryptography.algorithms.SHA512)
                .hasher()
                .hashBlocking(password.encodeToByteArray())
            return HashedPassword(hashed.decodeToString())
        }
    }
}

@JvmInline
@Serializable
value class Email(val value: String) {
    companion object {
        fun from(email: String): Email = Email(email)
    }
}

@JvmInline
@Serializable
value class NickName(val value: String)

@Serializable
data class User(
    val id: UserId,
    val version: Int = 0,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val username: UserName,
    @Transient
    val hashedPassword: HashedPassword? = null,
    val status: RowStatus = RowStatus.NORMAL,
    val role: UserRole = UserRole.USER,
    val email: Email? = null,
    val nickname: NickName,
    val avatarUrl: String? = null,
)

@Serializable
enum class Locale {
    EN,
    CN,
}

@Serializable
data class UserSetting(
    val id: TableId,
    var locale: Locale = Locale.CN,
    var appearance: String = "system",
)
