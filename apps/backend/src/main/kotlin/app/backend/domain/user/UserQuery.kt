package app.backend.domain.user

import model.user.Email
import model.user.User
import model.user.UserId
import model.user.UserName
import model.user.UserRole

interface UserQuery {

    suspend fun findUser(id: UserId): User?

    suspend fun findUser(username: UserName): User?

    suspend fun findUser(email: Email): User?

    suspend fun countUser(role: UserRole): Long
}
