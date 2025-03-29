package com.github.walkin.memos.graphql

import com.github.walkin.memos.domain.Inbox
import com.github.walkin.memos.query.FindInbox
import com.github.walkin.memos.query.InboxQuery
import com.github.walkin.memos.query.UserQuery
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class InboxGraphql(val userQuery: UserQuery, val inboxQuery: InboxQuery) {

  @QueryMapping
  suspend fun inboxes(): List<Inbox> {
    val requestUser = userQuery.getCurrentRequestOwner() ?: return emptyList()

    val inboxes = inboxQuery.listInBoxes(FindInbox(receiverId = requestUser.id))

    return inboxes
  }
}
