package com.github.walkin.memos.controller

import com.github.walkin.memos.MemosController
import com.github.walkin.memos.domain.SignIn
import com.github.walkin.memos.domain.SignUp
import com.github.walkin.memos.entity.EntityID
import com.github.walkin.memos.entity.User
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.security.JwtTokens
import com.github.walkin.security.SecurityJwtService
import com.github.walkin.usecase.CommandPublish
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@MemosController
@Validated
@RequestMapping("/auth")
class AuthResource(
  private val commandPublish: CommandPublish,
  private val userQuery: UserQuery,
  private val jwtService: SecurityJwtService,
) {

  @PostMapping("/refresh_token")
  suspend fun refreshToken(@RequestBody jwtTokens: JwtTokens): ResponseEntity<JwtTokens> {

    val accessToken =
      jwtTokens.refreshToken?.let { refreshToken ->
        val jwtPayload = jwtService.decodeRefreshToken(refreshToken)

        jwtService.accessToken(jwtPayload.username, jwtPayload.role)
      }

    return ResponseEntity.ok(
      JwtTokens(accessToken = accessToken, refreshToken = jwtTokens.refreshToken)
    )
  }

  @PostMapping("/status")
  suspend fun getAuthStatus(): ResponseEntity<User> =
    userQuery.getCurrentRequestOwner().let { ResponseEntity.ok(it) }

  @PostMapping("/signout") suspend fun signout() = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)

  @PostMapping("/signup")
  suspend fun signup(
    @RequestParam username: String,
    @RequestParam password: String,
  ): ResponseEntity<EntityID> =
    commandPublish.command(SignUp(username, password)).let { ResponseEntity.ok(it) }

  @PostMapping("/signip")
  suspend fun signIn(@RequestBody command: SignIn): ResponseEntity<JwtTokens> {
    return ResponseEntity.ok(commandPublish.command(command))
  }
}
