package com.github.walkin.memos.controller

import com.github.walkin.memos.DefaultPageSize
import com.github.walkin.memos.MemosController
import com.github.walkin.memos.domain.*
import com.github.walkin.memos.query.FindInbox
import com.github.walkin.memos.query.InboxQuery
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.usecase.CommandPublish
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@MemosController
class InboxResource(
  private val commandPublish: CommandPublish,
  private val userQuery: UserQuery,
  private val inboxQuery: InboxQuery,
) {

  @GetMapping("/inboxes")
  suspend fun listInboxes(
    @RequestParam user: String,
    @RequestParam(required = false, name = "pageSize") pageSize: Int,
    @RequestParam(required = false, name = "pageToken") pageToken: PageToken?,
  ): ResponseEntity<ListInboxesResponse> {
    val requestUser = userQuery.getCurrentRequestOwner()

    var limit: Int
    var offset = 0
    if (!pageToken.isNullOrBlank()) {
      val pageToken = pageToken.toPayload()

      limit = pageToken.limit
      offset = pageToken.offset
    } else {
      limit = pageSize
    }

    if (limit <= 0) {
      limit = DefaultPageSize
    }
    val limitPlusOne = limit + 1

    val inboxes =
      inboxQuery.listInBoxes(
        FindInbox(receiverId = requestUser.id, limit = limitPlusOne, offset = offset)
      )

    return ResponseEntity.ok(
      ListInboxesResponse(inboxes, PageTokenPayload(limit, limit + offset).encode())
    )
  }

  @PatchMapping("/inboxes")
  suspend fun updateInbox(request: UpdateInbox) {
    ResponseEntity.ok().body(commandPublish.command(request))
  }

  @DeleteMapping("/inboxes/{name}")
  suspend fun deleteInbox(@PathVariable name: Long): ResponseEntity<Unit> {
    commandPublish.command(DeleteInbox(name))
    return ResponseEntity.ok().build()
  }
}
