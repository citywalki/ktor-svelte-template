package com.github.walkin.memos.query

import com.github.walkin.memos.Entity
import com.github.walkin.memos.domain.Inbox
import com.github.walkin.memos.domain.InboxStatus
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service

@Service
class InboxQuery(private val database: R2dbcDatabase) {

  suspend fun listInBoxes(
    id: Long? = null,
    senderId: Long? = null,
    receiverId: Long? = null,
    status: InboxStatus? = null,
    limit: Int? = null,
    offset: Int? = 0,
  ): List<Inbox> {

    return database.runQuery {
      QueryDsl.from(Entity.inbox)
        .where {
          id?.let { Entity.inbox.id eq it }
          senderId?.let { Entity.inbox.senderId eq senderId }
          receiverId?.let { Entity.inbox.receiverId eq receiverId }
          status?.let { Entity.inbox.status eq status }
        }
        .apply {
          limit?.let { limit ->
            limit(limit)
            offset?.let { offset -> offset(offset) }
          }
        }
    }
  }
}
