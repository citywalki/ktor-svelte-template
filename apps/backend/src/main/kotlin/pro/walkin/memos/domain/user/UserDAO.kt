package pro.walkin.memos.domain.user

import domain.user.User

interface UserDAO {

    suspend fun insertUser(user: User): User
}
