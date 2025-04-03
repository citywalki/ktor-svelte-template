package com.github.walkin.memos.graphql

import com.github.walkin.memos.domain.MemosVisibility
import com.github.walkin.memos.domain.User
import com.github.walkin.memos.domain.UserId
import com.github.walkin.memos.domain.UserSetting
import com.github.walkin.memos.entity.*
import com.github.walkin.memos.query.UserQuery
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

data class UserSpaceType(val name: String)

data class FindUser(
  val username: String? = null,
  val id: UserId? = null,
  val role: UserRole? = null,
)

@Controller
class UserGraphql(private val userQuery: UserQuery) {

  @QueryMapping
  suspend fun currentUser(): User? {

    return userQuery.getCurrentRequestOwner()?.toModel()
  }

  @SchemaMapping(typeName = "User")
  suspend fun userSetting(user: User): UserSetting {

    val userSettings =
      UserSettingEntity.find {
          return@find UserSettingTable.user.eq(EntityID(user.id, UserTable))
        }
        .toList()

    val userSetting = UserSetting(id = user.id)

    userSettings.forEach {
      when (it.key.value) {
        UserSettingKey.LOCALE -> userSetting.locale = it.value
        UserSettingKey.APPEARANCE -> userSetting.appearance = it.value
        UserSettingKey.MEMO_VISIBILITY ->
          userSetting.memoVisibility = MemosVisibility.valueOf(it.value)
        else -> {}
      }
    }

    return userSetting
  }

  @SchemaMapping(typeName = "User")
  fun userSpaces(user: User): UserSpaceType? {

    return UserSpaceEntity.find { UserSpaceTable.user eq user.id }
      .map { UserSpaceType(name = "") }
      .singleOrNull()
  }

  @QueryMapping
  fun users(@Argument find: FindUser): List<User> {
    return UserEntity.find {
        val op = Op.TRUE
        find.id?.let { op.and { UserTable.id eq EntityID(it, UserTable) } }
        op
      }
      .map { it.toModel() }
  }

  @QueryMapping
  fun user(@Argument find: FindUser): User? = transaction {
    UserEntity.find {
        val op = Op.TRUE

        find.id?.apply { UserTable.id eq this }

        op
      }
      .map { it.toModel() }
      .singleOrNull()
  }
}
