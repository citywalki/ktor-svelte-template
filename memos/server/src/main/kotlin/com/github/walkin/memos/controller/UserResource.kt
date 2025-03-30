package com.github.walkin.memos.controller

import com.github.walkin.memos.MemosController
import com.github.walkin.memos.domain.UpdateUser
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.usecase.CommandPublish
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@MemosController
@Validated
class UserResource(private val commandPublish: CommandPublish, private val userQuery: UserQuery) {

  @PatchMapping("/users")
  suspend fun updateUser(@RequestBody updateUser: UpdateUser): ResponseEntity<Unit> {
    commandPublish.command(updateUser)
    return ResponseEntity.ok().build<Unit>()
  }

  @DeleteMapping("/users/{name}")
  suspend fun deleteUser(@PathVariable("name") name: String) {
    TODO()
  }
}
