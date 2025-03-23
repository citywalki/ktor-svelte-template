package com.github.walkin.memos.usecase.user

import com.github.walkin.memos.Entity
import com.github.walkin.memos.MemosExceptionFactory
import com.github.walkin.memos.domain.CreateUser
import com.github.walkin.memos.domain.User
import com.github.walkin.memos.query.FindUser
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.usecase.UseCase
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class CreateUserUsecase(val database: R2dbcDatabase, val userQuery: UserQuery) :
  UseCase<CreateUser, User>() {
  override suspend fun handle(command: CreateUser): User {

    userQuery.getUser(FindUser(username = command.username))?.let {
      throw MemosExceptionFactory.User.userSameExist()
    }
    return database.runQuery {
      QueryDsl.insert(Entity.user)
        .single(User(username = command.username, password = command.password))
    }
  }
}
