package pro.walkin.memos.domain.user.persistence

import domain.Email
import domain.User
import domain.UserId
import domain.UserName
import domain.UserRole

interface UserDAOFacade {
    suspend fun findUser(id: UserId): User?

    suspend fun findUser(username: UserName): User?

    suspend fun findUser(email: Email): User?

    suspend fun countUser(role: UserRole): Long

    suspend fun insertUser(user: User): User
}
