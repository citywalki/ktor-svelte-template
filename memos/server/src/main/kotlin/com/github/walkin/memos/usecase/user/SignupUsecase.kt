package com.github.walkin.memos.usecase.user

import com.github.walkin.memos.domain.SignUp
import com.github.walkin.memos.domain.UserId
import com.github.walkin.memos.entity.UserEntity
import com.github.walkin.memos.entity.UserRole
import com.github.walkin.memos.entity.UserSpaceEntity
import com.github.walkin.memos.entity.UserTable
import com.github.walkin.memos.query.GlobalSettingQuery
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.security.PasswordEncoder
import com.github.walkin.usecase.UseCase
import kotlin.reflect.KClass
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignupUsecase(
  private val globalSettingQuery: GlobalSettingQuery,
  private val userQuery: UserQuery,
  val passwordEncoder: PasswordEncoder,
) : UseCase<SignUp, UserId>() {

  override fun handle(command: SignUp): UserId = transaction {
    globalSettingQuery.getWorkspaceGeneralSetting().apply {
      if (disallowUserRegistration) {
        throw IllegalStateException("SignUpNotAllowed")
      }
    }

    val passwordHash = passwordEncoder.encode(command.password)

    val role =
      if (UserTable.selectAll().where { UserTable.role eq UserRole.HOST }.count() > 0) {
        UserRole.USER
      } else {
        UserRole.HOST
      }

    val user =
      UserEntity.new {
        username = command.username
        password = passwordHash
        this.role = role
      }

    UserSpaceEntity.new {
      name = "${command.username}'s default space"
      this.user = user
    }

     user.id.value
  }

  override fun getCommandType(): KClass<SignUp> {
    return SignUp::class
  }
}
