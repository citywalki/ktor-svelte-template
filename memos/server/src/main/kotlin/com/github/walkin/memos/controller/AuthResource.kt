package com.github.walkin.memos.controller

import com.github.walkin.memos.domain.SignIn
import com.github.walkin.memos.domain.SignUp
import com.github.walkin.memos.domain.TableId
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
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/auth")
class AuthResource(
  private val commandPublish: CommandPublish,
  private val userQuery: UserQuery,
  private val jwtService: SecurityJwtService,
) {

  @PostMapping("/refresh_token")
  fun refreshToken(@RequestBody jwtTokens: JwtTokens): ResponseEntity<JwtTokens> {

    val accessToken =
      jwtTokens.refreshToken?.let { refreshToken ->
        val jwtPayload = jwtService.decodeRefreshToken(refreshToken)

        jwtService.accessToken(jwtPayload.username, jwtPayload.role)
      }

    return ResponseEntity.ok(
      JwtTokens(accessToken = accessToken, refreshToken = jwtTokens.refreshToken)
    )
  }

  @PostMapping("/signout") fun signout() = ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)

  @PostMapping("/signup")
  fun signup(@RequestBody message: SignUp): ResponseEntity<TableId> =
    commandPublish.command(message).let { ResponseEntity.ok(it.value) }

  @PostMapping("/signip")
  fun signIn(@RequestBody command: SignIn): ResponseEntity<JwtTokens> {
    return ResponseEntity.ok(commandPublish.command(command))
  }
}
