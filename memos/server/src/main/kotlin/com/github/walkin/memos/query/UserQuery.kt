package com.github.walkin.memos.query

import com.github.walkin.memos.Entity
import com.github.walkin.memos.domain.*
import com.github.walkin.memos.store.UserSettingKey
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.single
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service

data class FindUser(val username: String? = null, val id: Long? = null, val role: UserRole? = null)

@Service
class UserQuery(private val database: R2dbcDatabase) {

  suspend fun getInstanceOwner() = getUser(FindUser(role = UserRole.HOST))

  suspend fun getUser(find: FindUser): User? =
    database.runQuery {
      QueryDsl.from(Entity.user)
        .where {
          find.id?.run { Entity.user.id eq this }
          find.username?.run { Entity.user.username eq this }
          find.role?.run { Entity.user.role eq this }
        }
        .singleOrNull()
    }

  suspend fun listUser(find: FindUser) =
    database.runQuery {
      QueryDsl.from(Entity.user).where { find.username?.run { Entity.user.username eq this } }
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

  suspend fun getUserSetting(userId: Long): UserSetting {
    val userSettings =
      database.runQuery {
        QueryDsl.from(Entity.userSetting).where { Entity.userSetting.unique.userId eq userId }
      }

    val userSetting = UserSetting(name = userId)

    userSettings.forEach {
      when (it.unique.key) {
        UserSettingKey.LOCALE -> userSetting.locale = it.value
        UserSettingKey.APPEARANCE -> userSetting.appearance = it.value
        UserSettingKey.MEMO_VISIBILITY ->
          userSetting.memoVisibility = MemosVisibility.valueOf(it.value)
        else -> {}
      }
    }

    return userSetting
  }

  suspend fun listUser() = database.runQuery { QueryDsl.from(Entity.user) }
}
