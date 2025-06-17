package pro.walkin.memos.domain.auth

import domain.Email
import domain.HashedPassword
import domain.NickName
import domain.RowStatus
import domain.User
import domain.UserId
import domain.UserName
import domain.UserRole
import org.komapper.r2dbc.R2dbcDatabase
import pro.walkin.logging.I18nMessages
import pro.walkin.memos.domain.system.persistence.SystemSettingDAOFacade
import pro.walkin.memos.domain.user.UserDAO
import pro.walkin.memos.domain.user.create
import pro.walkin.memos.domain.user.persistence.UserDAOFacade
import pro.walkin.memos.error.authMessages
import pro.walkin.memos.error.userMessages

class AuthService(
    private val database: R2dbcDatabase,
    private val userDAOFacade: UserDAOFacade,
    private val systemSettingDAOFacade: SystemSettingDAOFacade
) {

    suspend fun signinForEmail(email: Email, password: HashedPassword): User {
        val user = userDAOFacade.findUser(email) ?: throw I18nMessages.userMessages.userNotExist()

        systemSettingDAOFacade.findGeneralSystemSetting().apply {
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

    suspend fun signin(userName: UserName, password: HashedPassword): User {
        val user = database.runQuery {
            UserDAO.findUser(userName)
        } ?: throw I18nMessages.userMessages.userNotExist()

        systemSettingDAOFacade.findGeneralSystemSetting().apply {
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

    suspend fun signup(
        userName: UserName,
        password: HashedPassword
    ): User {
        systemSettingDAOFacade.findGeneralSystemSetting().apply {
            check(!disallowUserRegistration) {
                "SignUpNotAllowed"
            }
        }

        val role = if (userDAOFacade.countUser(UserRole.HOST) > 0) {
            UserRole.USER
        } else {
            UserRole.HOST
        }

        val user = userDAOFacade.insertUser(
            User(
                id = UserId.create(),
                username = userName,
                hashedPassword = password,
                role = role,
                nickname = NickName(userName.value),
            )
        )

        return user
    }

    fun getAuthStatus(): User? {
        TODO("Not yet implemented")
    }
}
