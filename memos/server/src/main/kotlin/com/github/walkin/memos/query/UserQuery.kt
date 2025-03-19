package com.github.walkin.memos.query

import com.github.walkin.memos.Entity
import com.github.walkin.memos.domain.User
import com.github.walkin.memos.domain.UserRole
import com.github.walkin.memos.domain.UserStats
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.single
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service

@Service
class UserQuery(private val database: R2dbcDatabase) {

  fun listAllUserStats(): List<UserStats> {
    TODO()
  }

  suspend fun getInstanceOwner() = getUser(role = UserRole.HOST)

  suspend fun getUser(id: Long? = null, username: String? = null, role: UserRole? = null): User? =
    database.runQuery {
      QueryDsl.from(Entity.user)
        .where {
          id?.run { Entity.user.id eq this }
          username?.run { Entity.user.username eq this }
          role?.run { Entity.user.role eq this }
        }
        .singleOrNull()
    }

  suspend fun listUser(username: String? = null) =
    database.runQuery {
      QueryDsl.from(Entity.user).where { username?.run { Entity.user.username eq this } }
    }

  suspend fun getCurrentRequestOwner(): User {
    return database.runQuery {
      QueryDsl.from(Entity.user)
        .where {
          // todo get current user id
          Entity.user.id eq 1
        }
        .single()
    }
  }

  suspend fun listUser() = database.runQuery { QueryDsl.from(Entity.user) }
}
