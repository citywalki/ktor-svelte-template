package pro.walkin.memos.domain.auth

import domain.RowStatus
import domain.user.HashedPassword
import domain.user.NickName
import domain.user.User
import domain.user.UserId
import domain.user.UserName
import domain.user.UserRole
import pro.walkin.logging.I18nMessages
import pro.walkin.memos.domain.system.persistence.SystemSettingDAOFacade
import pro.walkin.memos.domain.user.UserDAO
import pro.walkin.memos.domain.user.UserQuery
import pro.walkin.memos.domain.user.persistence.create
import pro.walkin.memos.error.authMessages
import pro.walkin.memos.error.userMessages

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
