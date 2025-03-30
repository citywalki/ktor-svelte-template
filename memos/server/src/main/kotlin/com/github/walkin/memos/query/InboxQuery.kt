package com.github.walkin.memos.query

import com.github.walkin.memos.Entity
import com.github.walkin.memos.domain.InboxStatus
import com.github.walkin.memos.entity.EntityID
import com.github.walkin.memos.entity.Inbox
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service

data class FindInbox(
  val id: EntityID? = null,
  val senderId: EntityID? = null,
  val receiverId: EntityID? = null,
  val status: InboxStatus? = null,
  val limit: Int? = null,
  val offset: Int? = null,
)

@Service
class InboxQuery(private val database: R2dbcDatabase) {

  suspend fun listInBoxes(find: FindInbox): List<Inbox> {
    return database.runQuery {
      QueryDsl.from(Entity.inbox)
        .where {
          find.id?.let { Entity.inbox.id eq it }
          find.senderId?.let { Entity.inbox.senderId eq it }
          find.receiverId?.let { Entity.inbox.receiverId eq it }
          find.status?.let { Entity.inbox.status eq it }
        }
        .apply {
          find.limit?.let { limit ->
            limit(limit)
            find.offset?.let { offset -> offset(offset) }
          }
        }
    }
  }
}
