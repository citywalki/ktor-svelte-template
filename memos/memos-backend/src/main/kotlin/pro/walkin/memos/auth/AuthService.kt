package pro.walkin.memos.auth

import domain.Email
import domain.HashedPassword
import domain.NickName
import domain.RowStatus
import domain.User
import domain.UserId
import domain.UserName
import domain.UserRole
import org.komapper.jdbc.JdbcDatabase
import pro.walkin.logging.I18nMessages
import pro.walkin.memos.error.authMessages
import pro.walkin.memos.error.userMessages
import pro.walkin.memos.system.SystemSettingDAO
import pro.walkin.memos.user.UserDAO
import pro.walkin.memos.user.create

class AuthService(
    private val database: JdbcDatabase
) {

    fun signinForEmail(email: Email, password: HashedPassword, neverExpire: Boolean? = false): User {
        val user = database.runQuery {
            UserDAO.findUser(email)
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

    fun signin(userName: UserName, password: HashedPassword, neverExpire: Boolean? = false): User {
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

    fun signup(
        userName: UserName,
        password: HashedPassword
    ): User {
        database.runQuery {
            SystemSettingDAO.findGeneralSystemSetting()
        }.apply {
            check(!disallowUserRegistration) {
                "SignUpNotAllowed"
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

    fun getAuthStatus(): User? {
        TODO("Not yet implemented")
    }
}
