package com.github.walkin.memos.graphql

import com.github.walkin.memos.entity.Inbox
import com.github.walkin.memos.entity.InboxEntity
import com.github.walkin.memos.entity.InboxTable
import com.github.walkin.memos.query.UserQuery
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class InboxGraphql(val userQuery: UserQuery) {

  @QueryMapping
  suspend fun inboxes(): List<Inbox> {
    val requestUser = userQuery.getCurrentRequestOwner() ?: return emptyList()

    return InboxEntity.find { InboxTable.receiverId eq requestUser.id }
      .map { it.toModel() }
      .toList()
  }
}
