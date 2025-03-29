package com.github.walkin.memos.controller

import com.github.walkin.memos.MemosController
import com.github.walkin.memos.domain.CreateMemo
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.usecase.CommandPublish
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@MemosController
class MemoController(private val userQuery: UserQuery, private val commandPublish: CommandPublish) {

  @PostMapping("/memo")
  suspend fun createMemo(@RequestParam("name") name: String): ResponseEntity<Nothing> {
    val requestOwner = userQuery.getCurrentRequestOwner() ?: return ResponseEntity.badRequest().build()

    val command = CreateMemo(requestOwner.id, "")
    commandPublish.command(command)

    return ResponseEntity.ok().build()
  }
}
