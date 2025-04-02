package com.github.walkin.memos.domain

import com.github.walkin.usecase.Command
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

@Serializable data class UpdateInbox(val id: Long, val status: InboxStatus) : Command<Unit>()

@Serializable data class DeleteInbox(val id: Long) : Command<Unit>()
