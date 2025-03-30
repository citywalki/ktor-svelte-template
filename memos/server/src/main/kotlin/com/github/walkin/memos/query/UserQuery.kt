package com.github.walkin.memos.query

import com.github.walkin.memos.Entity
import com.github.walkin.memos.domain.*
import com.github.walkin.memos.entity.*
import com.github.walkin.memos.store.UserSettingKey
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.single
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Service

data class FindUser(val username: String? = null, val id: Long? = null, val role: UserRole? = null)

data class FindUserSpace(val user: EntityID)

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

  suspend fun getCurrentRequestOwner(): User? {
    val securityContext = ReactiveSecurityContextHolder.getContext().awaitFirstOrNull()

    val username = securityContext?.authentication?.principal as String?

    return username?.let {
      database.runQuery {
        QueryDsl.from(Entity.user).where { Entity.user.username eq username }.single()
      }
    }
  }

  suspend fun getUserSetting(userId: Long): UserSetting {
    val userSettings =
      database.runQuery {
        QueryDsl.from(Entity.userSetting).where { Entity.userSetting.unique.userId eq userId }
      }

    val userSetting = UserSetting(id = userId)

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

  suspend fun listUserSpaces(find: FindUserSpace): List<UserSpace> {

    return database.runQuery {
      QueryDsl.from(Entity.userSpace).where { Entity.userSpace.userId eq find.user }
    }
  }
}
