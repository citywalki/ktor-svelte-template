package app.backend.domain.auth

import model.RowStatus
import model.user.HashedPassword
import model.user.NickName
import model.user.User
import model.user.UserId
import model.user.UserName
import model.user.UserRole
import pro.walkin.logging.I18nMessages
import app.backend.domain.system.persistence.SystemSettingDAOFacade
import app.backend.domain.user.UserDAO
import app.backend.domain.user.UserQuery
import app.backend.domain.user.persistence.create
import app.backend.error.authMessages
import app.backend.error.userMessages

class AuthService(
    private val userDAO: UserDAO,
    private val userQuery: UserQuery,
    private val systemSettingDAOFacade: SystemSettingDAOFacade
) {

    suspend fun signin(userName: UserName, password: HashedPassword): User {
        val user = userQuery.findUser(userName) ?: throw IllegalStateException(I18nMessages.userMessages.userNotExist())

        systemSettingDAOFacade.findGeneralSystemSetting().apply {
            if (disallowPasswordAuth && user.role == UserRole.USER) {
                throw I18nMessages.authMessages.passwordSigninNotAllowed()
            }
        }

        if (user.status == RowStatus.ARCHIVED) {
            throw I18nMessages.authMessages.userHasBeenArchived(user.username)
        }

        if (!password.value.contentEquals(user.hashedPassword?.value)) {
            throw I18nMessages.userMessages.userPasswordNotMatch()
        }

        return user
    }

    suspend fun signup(
        userName: UserName,
        password: HashedPassword
    ): User {
        systemSettingDAOFacade.findGeneralSystemSetting()
            .apply {
                check(!disallowUserRegistration) {
                    "SignUpNotAllowed"
                }
            }

        val role = userQuery.countUser(UserRole.HOST).let {
            if (it > 0) UserRole.USER else UserRole.HOST
        }

        return userDAO.insertUser(
            User(
                id = UserId.create(),
                username = userName,
                hashedPassword = password,
                role = role,
                nickname = NickName(userName.value),
            )
        )
    }

    fun getAuthStatus(): User? {
        TODO("Not yet implemented")
    }
}
