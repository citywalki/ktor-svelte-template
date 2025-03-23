package com.github.walkin.memos.auth

import com.github.walkin.memos.query.FindUser
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.security.Account
import com.github.walkin.security.SecurityAuthService
import org.springframework.stereotype.Component

@Component
class AuthSecurityService(private val userQuery: UserQuery) : SecurityAuthService {
  override suspend fun findByUsername(username: String?): Account? =
    userQuery.getUser(FindUser(username = username))?.let {
      Account(username = it.username, password = it.password, role = setOf(""))
    }
}
