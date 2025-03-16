package com.github.walkin.memos.usecase.inbox

import com.github.walkin.memos.Entity
import com.github.walkin.memos.domain.DeleteInbox
import com.github.walkin.usecase.UseCase
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service

@Service
class DeleteInboxUsecase(private val database: R2dbcDatabase) : UseCase<DeleteInbox, Unit>() {
  override suspend fun handle(command: DeleteInbox) {
    database.runQuery { QueryDsl.delete(Entity.inbox).where { Entity.inbox.id eq command.id } }
  }
}
