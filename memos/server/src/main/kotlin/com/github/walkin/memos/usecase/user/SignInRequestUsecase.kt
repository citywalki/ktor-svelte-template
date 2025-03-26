package com.github.walkin.memos.usecase.user

import com.github.walkin.memos.MemosExceptionFactory
import com.github.walkin.memos.domain.SignInRequest
import com.github.walkin.memos.domain.User
import com.github.walkin.memos.domain.UserRole
import com.github.walkin.memos.domain.accessTokenDuration
import com.github.walkin.memos.query.FindUser
import com.github.walkin.memos.query.GlobalSettingQuery
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.shared.entity.RowStatus
import com.github.walkin.usecase.UseCase
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.springframework.stereotype.Component

@Component
class SignInRequestUsecase(val userQuery: UserQuery, val globalSettingQuery: GlobalSettingQuery) :
  UseCase<SignInRequest, String>() {
  override suspend fun handle(command: SignInRequest): String {
    val user =
      userQuery.getUser(FindUser(username = command.username))
        ?: throw MemosExceptionFactory.User.userNotExist()

    globalSettingQuery.getWorkspaceGeneralSetting().apply {
      if (disallowPasswordAuth && user.role == UserRole.USER) {
        throw IllegalStateException("password signin is not allowed")
      }
    }
    if (user.status == RowStatus.ARCHIVED) {
      throw IllegalStateException("user has been archived with username ${user.username}")
    }

    var expireTime = Clock.System.now().plus(accessTokenDuration)
    if (command.neverExpire) {
      expireTime = Clock.System.now().plus((100 * 365 * 24).toDuration(DurationUnit.HOURS))
    }
    doSignIn(user, expireTime)

    return ""
  }

  fun doSignIn(user: User, expireTime: Instant) {}
}
