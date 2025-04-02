package com.github.walkin.memos.controller

import com.github.walkin.memos.MemosController
import com.github.walkin.memos.domain.*
import com.github.walkin.memos.domain.TableId
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.usecase.CommandPublish
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable

@MemosController
class InboxResource(private val commandPublish: CommandPublish, private val userQuery: UserQuery) {

  @PatchMapping("/inboxes")
  fun updateInbox(request: UpdateInbox) {
    ResponseEntity.ok().body(commandPublish.command(request))
  }

  @DeleteMapping("/inboxes/{name}")
  suspend fun deleteInbox(@PathVariable name: TableId): ResponseEntity<Unit> {
    commandPublish.command(DeleteInbox(name))
    return ResponseEntity.ok().build()
  }
}
