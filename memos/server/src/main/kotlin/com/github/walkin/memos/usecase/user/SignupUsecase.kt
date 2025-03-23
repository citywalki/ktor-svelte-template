package com.github.walkin.memos.usecase.user

import com.github.walkin.memos.Entity
import com.github.walkin.memos.domain.SignupRequest
import com.github.walkin.memos.domain.User
import com.github.walkin.memos.domain.UserRole
import com.github.walkin.memos.query.FindUser
import com.github.walkin.memos.query.GlobalSettingQuery
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.usecase.UseCase
import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.SHA512
import kotlinx.io.bytestring.encodeToByteString
import kotlinx.io.bytestring.toHexString
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
) : UseCase<SignupRequest, User>() {
  @OptIn(ExperimentalStdlibApi::class)
  override suspend fun handle(command: SignupRequest): User {
    globalSettingQuery.getWorkspaceGeneralSetting().apply {
      if (disallowUserRegistration) {
        throw IllegalStateException("SignUpNotAllowed")
      }
    }
    val passwordHash =
      CryptographyProvider.Companion.Default.get(SHA512)
        .hasher()
        .hash(command.password.encodeToByteString())

    val user = User(username = command.username, password = passwordHash.toHexString())
    user.role =
      userQuery.getUser(FindUser(role = UserRole.HOST))?.let { UserRole.USER } ?: UserRole.HOST

    return database.runQuery { QueryDsl.insert(Entity.user).single(user) }
  }
}
