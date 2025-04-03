package com.github.walkin.memos.query

import com.github.walkin.memos.entity.*
import org.springframework.stereotype.Service

@Service
class UserQuery {

  fun getCurrentRequestOwner(): UserEntity? {

    val username = ""

    return UserEntity.find { UserTable.username eq username }.singleOrNull()
  }
}
