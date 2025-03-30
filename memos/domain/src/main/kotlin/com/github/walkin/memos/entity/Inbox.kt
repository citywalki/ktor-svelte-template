package com.github.walkin.memos.entity

import com.github.walkin.memos.domain.PageToken
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
enum class InboxStatus {
  UNREAD,
  ARCHIVED,
}

@Serializable
enum class InboxMessageType {
  TYPE_UNSPECIFIED,
  MEMO_COMMENT,
  VERSION_UPDATE,
}

@Serializable data class InboxMessage(val type: InboxMessageType, val activityId: Long? = null)

@Serializable
data class Inbox(
  val id: Long? = null,
  val createdTs: LocalDateTime,
  val senderId: Long? = null,
  val receiverId: Long? = null,
  val status: InboxStatus,
  val message: InboxMessage,
)

data class ListInboxesResponse(val inboxEntities: List<Inbox>, val nextPageToken: PageToken)
