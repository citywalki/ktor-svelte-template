package pro.walkin.memos.domain.user.persistence

import domain.user.User
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
