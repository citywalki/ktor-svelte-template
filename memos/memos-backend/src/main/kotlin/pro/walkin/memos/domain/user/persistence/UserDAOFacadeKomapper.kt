package pro.walkin.memos.domain.user.persistence

import domain.Email
import domain.User
import domain.UserId
import domain.UserName
import domain.UserRole
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.operator.count
import org.komapper.core.dsl.query.map
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase
import pro.walkin.memos.domain.user.user

class UserDAOFacadeKomapper(
    private val db: R2dbcDatabase,
) : UserDAOFacade {
    private val u = Meta.user

    override suspend fun findUser(id: UserId): User? {
        val query = QueryDsl.from(u).where {
            u.id eq id
        }.singleOrNull()

        return db.runQuery(query)
    }

    override suspend fun findUser(username: UserName): User? {
        val query = QueryDsl.from(u).where {
            u.username eq username
        }.singleOrNull()

        return db.runQuery(query)
    }

    override suspend fun findUser(email: Email): User? {
        val query = QueryDsl.from(u).where {
            u.email eq email
        }.singleOrNull()

        return db.runQuery(query)
    }

    override suspend fun countUser(role: UserRole): Long {
        val query = QueryDsl.from(u).where {
            u.role eq role
        }.select(count()).map { it ?: 0 }

        return db.runQuery(query)
    }

    override suspend fun insertUser(user: User): User {
        val query = QueryDsl.insert(u).single(user)

        return db.runQuery(query)
    }
}
