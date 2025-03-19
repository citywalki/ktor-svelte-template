package com.github.walkin.memos.controller

import com.github.walkin.memos.MemosController
import com.github.walkin.memos.domain.SignInRequest
import com.github.walkin.memos.domain.SignupRequest
import com.github.walkin.memos.domain.User
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.usecase.CommandPublish
import jakarta.annotation.security.PermitAll
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@MemosController
@Validated
@RequestMapping("/auth")
class AuthResource(private val commandPublish: CommandPublish, private val userQuery: UserQuery) {
  @PostMapping("/status")
  @PermitAll
  suspend fun getAuthStatus(): ResponseEntity<User> =
    userQuery.getCurrentRequestOwner().let { ResponseEntity.ok(it) }

  @PostMapping("/signin/sso") suspend fun signInWithSSO() {}

  @PostMapping("/signin")
  suspend fun signin(
    @RequestParam username: String,
    @RequestParam password: String,
    @RequestParam neverExpire: Boolean,
  ) {
    return commandPublish.command(SignInRequest(username, password, neverExpire)).let {
      ResponseEntity.ok(it)
    }
  }

  @PostMapping("/signout") suspend fun signout() = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)

  @PostMapping("/signup")
  suspend fun signup(
    @RequestParam username: String,
    @RequestParam password: String,
  ): ResponseEntity<User> =
    commandPublish.command(SignupRequest(username, password)).let { ResponseEntity.ok(it) }
}
