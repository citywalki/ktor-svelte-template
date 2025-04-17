package service

import MemosExceptionFactory
import domain.HashedPassword
import domain.NickName
import domain.RowStatus
import domain.User
import domain.UserId
import domain.UserName
import domain.UserRole
import entity.user
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import query.countUser
import query.findGeneralSystemSetting
import query.findUser
import service.Service.Companion.generateId

suspend fun Service.signin(userName: UserName, password: HashedPassword) =
    database.withTransaction {
        val user = query.findUser(userName) ?: throw MemosExceptionFactory.UserExceptions.userNotExist()

        query.findGeneralSystemSetting().apply {
            if (disallowPasswordAuth && user.role == UserRole.USER) {
                throw IllegalStateException("password signin is not allowed")
            }
        }

        if (user.status == RowStatus.ARCHIVED) {
            throw IllegalStateException("user has been archived with username ${user.username}")
        }

        if (password != user.hashedPassword) {
            throw MemosExceptionFactory.UserExceptions.userPasswordNotMatch()
        }

        user
    }

suspend fun Service.signup(userName: UserName, password: HashedPassword) = database.withTransaction {
    query.findGeneralSystemSetting().apply {
        if (disallowUserRegistration) {
            throw IllegalStateException("SignUpNotAllowed")
        }
    }

    val role = if (query.countUser(UserRole.HOST) > 0) {
        UserRole.USER
    } else {
        UserRole.HOST
    }

    val user = database.runQuery {
        QueryDsl.insert(Meta.user).single(
            User(
                id = UserId(generateId()),
                username = userName,
                hashedPassword = password,
                role = role,
                nickname = NickName(userName.value),
            )
        )
    }

    user
}
