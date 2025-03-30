package com.github.walkin.memos.entity

import com.github.walkin.memos.domain.InboxMessage
import com.github.walkin.memos.domain.InboxStatus
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.komapper.annotation.*

@Serializable
@KomapperEntity
@KomapperTable(name = "inbox")
data class Inbox(
  @KomapperId val id: EntityID,
  @KomapperVersion val version: Int = 0,
  @KomapperCreatedAt val createdAt: LocalDateTime? = null,
  @KomapperUpdatedAt val updatedAt: LocalDateTime? = null,
  val senderId: EntityID? = null,
  val receiverId: EntityID? = null,
  @KomapperEnum(type = EnumType.NAME) val status: InboxStatus,
  val message: InboxMessage,
)
