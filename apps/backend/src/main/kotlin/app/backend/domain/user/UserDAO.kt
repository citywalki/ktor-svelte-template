package app.backend.domain.user

import model.user.User

interface UserDAO {

    suspend fun insertUser(user: User): User
}
