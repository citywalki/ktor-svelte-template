package memos.auth

import domain.*
import memos.error.authMessages
import memos.error.userMessages
import memos.system.SystemSettingDAO
import memos.user.UserDAO
import memos.user.create
import org.komapper.jdbc.JdbcDatabase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pro.walkin.logging.I18nMessages

interface AuthService {
    fun signin(userName: UserName, password: HashedPassword, neverExpire: Boolean? = false): User
    fun signup(
        userName: UserName,
        password: HashedPassword
    ): User

    fun getAuthStatus(): User?
}

@Service
@Transactional
class SpringAuthService(
    val database: JdbcDatabase
) : AuthService {
    override fun signin(
        userName: UserName,
        password: HashedPassword,
        neverExpire: Boolean?
    ): User {
        val user = database.runQuery {
            UserDAO.findUser(userName)
        } ?: throw I18nMessages.userMessages.userNotExist()

        database.runQuery {
            SystemSettingDAO.findGeneralSystemSetting()
        }.apply {
            if (disallowPasswordAuth && user.role == UserRole.USER) {
                throw I18nMessages.authMessages.passwordSigninNotAllowed()
            }
        }

        if (user.status == RowStatus.ARCHIVED) {
            throw I18nMessages.authMessages.userHasBeenArchived(user.username)
        }

        if (password != user.hashedPassword) {
            throw I18nMessages.userMessages.userPasswordNotMatch()
        }

        return user
    }

    override fun signup(
        userName: UserName,
        password: HashedPassword
    ): User {
        database.runQuery {
            SystemSettingDAO.findGeneralSystemSetting()
        }.apply {
            if (disallowUserRegistration) {
                throw IllegalStateException("SignUpNotAllowed")
            }
        }

        val role = if (database.runQuery {
                UserDAO.countUser(UserRole.HOST)
            } > 0
        ) {
            UserRole.USER
        } else {
            UserRole.HOST
        }

        val user = database.runQuery {
            UserDAO.insertUser(
                User(
                    id = UserId.create(),
                    username = userName,
                    hashedPassword = password,
                    role = role,
                    nickname = NickName(userName.value),
                )
            )
        }

        return user
    }

    override fun getAuthStatus(): User? {
        TODO("Not yet implemented")
    }
}
