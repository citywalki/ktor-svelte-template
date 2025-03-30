package com.github.walkin.memos.usecase.user

import com.github.walkin.memos.Entity
import com.github.walkin.memos.domain.SignUp
import com.github.walkin.memos.entity.User
import com.github.walkin.memos.entity.UserRole
import com.github.walkin.memos.entity.UserSpace
import com.github.walkin.memos.query.FindUser
import com.github.walkin.memos.query.GlobalSettingQuery
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.security.PasswordEncoder
import com.github.walkin.usecase.UseCase
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SignupUsecase(
  private val globalSettingQuery: GlobalSettingQuery,
  private val userQuery: UserQuery,
  private val database: R2dbcDatabase,
  val passwordEncoder: PasswordEncoder,
) : UseCase<SignUp, User>() {

  override suspend fun handle(command: SignUp): User {
    globalSettingQuery.getWorkspaceGeneralSetting().apply {
      if (disallowUserRegistration) {
        throw IllegalStateException("SignUpNotAllowed")
      }
    }

    val passwordHash = passwordEncoder.encode(command.password)

    var user = User(username = command.username, password = passwordHash)
    user.role =
      userQuery.getUser(FindUser(role = UserRole.HOST))?.let { UserRole.USER } ?: UserRole.HOST

    user = database.runQuery { QueryDsl.insert(Entity.user).single(user) }

    database.runQuery {
      QueryDsl.insert(Entity.userSpace)
        .single(UserSpace(name = "${command.username}'s default space", userId = user.id))
    }

    return user
  }
}
