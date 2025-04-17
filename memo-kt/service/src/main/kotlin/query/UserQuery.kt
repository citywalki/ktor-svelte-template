package query

import domain.User
import domain.UserId
import domain.UserName
import domain.UserRole
import entity.user
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.operator.count
import org.komapper.core.dsl.query.singleOrNull
import utils.SecurityContextHolder

val user = Meta.user

suspend fun Query.findUser(id: UserId): User? =
    database.runQuery {
        QueryDsl.from(user).where {
            user.id eq id
        }.singleOrNull()
    }

suspend fun Query.findUser(username: UserName): User? =
    database.runQuery {
        QueryDsl.from(user).where {
            user.username eq username
        }.singleOrNull()
    }

suspend fun Query.getAuthStatus(): User? {

    val securityContext = SecurityContextHolder.get()

    return findUser(securityContext.userId!!)
}

suspend fun Query.countUser(role: UserRole): Long =
    database.runQuery {
        QueryDsl.from(user).where {
            user.role eq role
        }.select(count(user.id))
    } ?: 0
