package pro.walkin.memos.domain.user

import domain.user.Email
import domain.user.User
import domain.user.UserId
import domain.user.UserName
import domain.user.UserRole

interface UserQuery {

    suspend fun findUser(id: UserId): User?

    suspend fun findUser(username: UserName): User?

    suspend fun findUser(email: Email): User?

    suspend fun countUser(role: UserRole): Long
}
