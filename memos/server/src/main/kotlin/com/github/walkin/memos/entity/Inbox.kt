package com.github.walkin.memos.entity

import com.github.walkin.memos.domain.InboxMessage
import com.github.walkin.memos.domain.InboxStatus
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.EntityID

@Serializable
data class Inbox(
  val id: Long,
  val version: Int = 0,
  val createdAt: LocalDateTime? = null,
  val updatedAt: LocalDateTime? = null,
  val senderId: Long? = null,
  val receiverId: Long? = null,
  val status: InboxStatus,
  val message: InboxMessage? = null,
)

object InboxTable : LongIdTable("inbox") {
  val version = integer("version")
  val senderId = reference("sender_id", UserTable)
  val receiverId = reference("receiver_id", UserTable)
  val status = enumeration("status", InboxStatus::class)
}

class InboxEntity(id: EntityID<Long>) : LongIdEntity(id, InboxTable) {
  companion object : LongIdEntityClass<InboxEntity>(InboxTable)

  val status by InboxTable.status

  fun toModel(): Inbox {
    return Inbox(id = id.value, status = status)
  }
}
