package com.github.walkin.memos.usecase.user

import com.github.walkin.memos.MemosExceptionFactory
import com.github.walkin.memos.domain.SignIn
import com.github.walkin.memos.domain.accessTokenDuration
import com.github.walkin.memos.entity.UserEntity
import com.github.walkin.memos.entity.UserRole
import com.github.walkin.memos.entity.UserTable
import com.github.walkin.memos.query.GlobalSettingQuery
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.security.JwtTokens
import com.github.walkin.security.PasswordEncoder
import com.github.walkin.shared.entity.RowStatus
import com.github.walkin.usecase.UseCase
import kotlin.reflect.KClass
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.springframework.stereotype.Component

@Component
class SignInRequestUsecase(
  val userQuery: UserQuery,
  val globalSettingQuery: GlobalSettingQuery,
  val passwordEncoder: PasswordEncoder,
) : UseCase<SignIn, JwtTokens>() {
  override fun handle(command: SignIn): JwtTokens {
    val user =
      UserEntity.find { UserTable.username eq command.username }.singleOrNull()
        ?: throw MemosExceptionFactory.UserExceptions.userNotExist()

    globalSettingQuery.getWorkspaceGeneralSetting().apply {
      if (disallowPasswordAuth && user.role == UserRole.USER) {
        throw IllegalStateException("password signin is not allowed")
      }
    }
    if (user.rowStatus == RowStatus.ARCHIVED) {
      throw IllegalStateException("user has been archived with username ${user.username}")
    }

    //    if (passwordEncoder.encode(command.password) != user.password) {
    //      throw MemosExceptionFactory.UserExceptions.userPasswordNotMatch()
    //    }

    var expireTime = Clock.System.now().plus(accessTokenDuration)
    if (command.neverExpire) {
      expireTime = Clock.System.now().plus((100 * 365 * 24).toDuration(DurationUnit.HOURS))
    }
    doSignIn(user, expireTime)

    return JwtTokens("", "")
  }

  fun doSignIn(user: UserEntity, expireTime: Instant) {}

  override fun getCommandType(): KClass<SignIn> {
    return SignIn::class
  }
}
