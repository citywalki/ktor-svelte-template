package com.github.walkin.memos.usecase.inbox

import com.github.walkin.memos.Entity
import com.github.walkin.memos.domain.UpdateInbox
import com.github.walkin.memos.entity.Inbox
import com.github.walkin.memos.query.InboxQuery
import com.github.walkin.usecase.UseCase
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service

@Service
class UpdateInboxUsecase(private val database: R2dbcDatabase, private val inboxQuery: InboxQuery) :
  UseCase<UpdateInbox, Inbox>() {
  override suspend fun handle(command: UpdateInbox): Inbox {

    return database
      .runQuery {
        QueryDsl.update(Entity.inbox)
          .set { Entity.inbox.status eq command.status }
          .where { Entity.inbox.id eq command.id }
          .returning()
      }
      .single()
  }
}
