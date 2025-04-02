package com.github.walkin.memos.usecase.inbox

import com.github.walkin.memos.domain.UpdateInbox
import com.github.walkin.memos.entity.InboxTable
import com.github.walkin.usecase.UseCase
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UpdateInboxUsecase() : UseCase<UpdateInbox, Unit>() {
  override fun handle(command: UpdateInbox) {
    InboxTable.update({ InboxTable.id eq command.id }) { it[status] = command.status }
  }

  override fun getCommandType() = UpdateInbox::class
}
