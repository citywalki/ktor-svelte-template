package com.github.walkin.memos.usecase.user

import com.github.walkin.memos.domain.SignUp
import com.github.walkin.memos.domain.UserId
import com.github.walkin.memos.entity.*
import com.github.walkin.security.PasswordEncoder
import com.github.walkin.usecase.UseCase
import kotlin.reflect.KClass
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class SignupUsecase(val passwordEncoder: PasswordEncoder) : UseCase<SignUp, UserId>() {

  override fun handle(command: SignUp): UserId = transaction {
    SystemSettingEntity.findGeneralSetting().apply {
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
