package domain

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
