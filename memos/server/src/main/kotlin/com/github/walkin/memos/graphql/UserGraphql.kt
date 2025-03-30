package com.github.walkin.memos.graphql

import com.github.walkin.memos.entity.User
import com.github.walkin.memos.entity.UserSetting
import com.github.walkin.memos.query.FindUser
import com.github.walkin.memos.query.FindUserSpace
import com.github.walkin.memos.query.UserQuery
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class UserGraphql(private val userQuery: UserQuery) {

  @QueryMapping
  suspend fun currentUser(): User? {
    return userQuery.getCurrentRequestOwner()
  }

  @SchemaMapping(typeName = "User")
  suspend fun userSetting(user: User): UserSetting {
    return userQuery.getUserSetting(user.id)
  }

  @SchemaMapping(typeName = "User")
  suspend fun userSpaces(user: User) = userQuery.listUserSpaces(FindUserSpace(user = user.id))

  @QueryMapping
  suspend fun users(@Argument find: FindUser): List<User> {
    return userQuery.listUser(find)
  }

  @QueryMapping
  suspend fun user(@Argument findUser: FindUser): User? {
    return userQuery.getUser(findUser)
  }
}
