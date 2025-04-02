package com.github.walkin.memos.query

import com.github.walkin.memos.domain.*
import com.github.walkin.memos.entity.*
import org.springframework.stereotype.Service

data class FindUser(
  val username: String? = null,
  val id: UserId? = null,
  val role: UserRole? = null,
)

data class FindUserSpace(val user: UserId)

@Service
class UserQuery() {

  fun getCurrentRequestOwner(): UserEntity? {

    val username = ""

    return UserEntity.find { UserTable.username eq username }.singleOrNull()
  }
}
