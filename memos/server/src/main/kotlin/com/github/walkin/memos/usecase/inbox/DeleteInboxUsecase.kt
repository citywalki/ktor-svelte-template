package com.github.walkin.memos.usecase.inbox

import com.github.walkin.memos.domain.DeleteInbox
import com.github.walkin.memos.entity.InboxTable
import com.github.walkin.usecase.UseCase
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteInboxUsecase() : UseCase<DeleteInbox, Unit>() {
  override fun handle(command: DeleteInbox) {
    InboxTable.deleteWhere { InboxTable.id.eq(command.id) }
  }

  override fun getCommandType() = DeleteInbox::class
}
