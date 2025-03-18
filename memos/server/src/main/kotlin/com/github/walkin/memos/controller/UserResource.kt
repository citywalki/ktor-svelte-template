package com.github.walkin.memos.controller

import com.github.walkin.memos.MemosController
import com.github.walkin.memos.domain.*
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.usecase.CommandPublish
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@MemosController
@Validated
class UserResource(private val commandPublish: CommandPublish, private val userQuery: UserQuery) {

  @GetMapping("/users")
  suspend fun listUsers(): ResponseEntity<Map<String, List<User>>> =
    userQuery.listUser().let { ResponseEntity.ok(mapOf("users" to it)) }

  @PostMapping("/users")
  suspend fun createUser(@RequestBody createUser: CreateUser): ResponseEntity<User> =
    commandPublish.command(createUser).let { ResponseEntity.ok(it) }

  @PatchMapping("/users/{id}")
  suspend fun updateUser(
    @PathVariable id: String,
    @RequestBody user: User?,
  ): ResponseEntity<Unit?> {
    user?.let {
      commandPublish.command(UpdateUser(id, name = user.username, password = it.password))
    }
    return ResponseEntity.ok().build<Unit>()
  }

  @GetMapping("/users:search")
  suspend fun searchUsers(@RequestParam filter: String): ResponseEntity<List<User>> = TODO()

  @GetMapping("/users:username")
  suspend fun getUserByName(@RequestParam("username") name: String): ResponseEntity<User> {
    return userQuery.getUser(username = name).let { ResponseEntity.ok(it) }
  }

  @DeleteMapping("/users/{name}")
  suspend fun deleteUser(@PathVariable("name") name: String) {
    TODO()
  }

  @GetMapping("/users/{name}/access_token")
  suspend fun listUserAccessTokens(@PathVariable("name") name: String) {
    TODO()
  }

  @PostMapping("/users/{name}/access_token")
  suspend fun createUserAccessToken(
    @PathVariable("name") name: String,
    @RequestBody request: CreateUserAccessTokenRequest,
  ) {
    TODO()
  }

  @DeleteMapping("/users/{name}/access_token/{accessToken}")
  suspend fun deleteUserAccessToken(
    @PathVariable("name") name: String,
    @PathVariable("accessToken") accessToken: String,
  ) {
    TODO()
  }

  @GetMapping("/users/{name}/setting")
  suspend fun getUserSetting(@PathVariable name: String): ResponseEntity<UserSetting> {
    TODO()
  }
}
