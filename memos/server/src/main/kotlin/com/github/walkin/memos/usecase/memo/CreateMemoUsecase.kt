package com.github.walkin.memos.usecase.memo

import com.github.walkin.memos.Entity
import com.github.walkin.memos.MemosExceptionFactory
import com.github.walkin.memos.domain.*
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.memos.query.WorkspaceSettingQuery
import com.github.walkin.memos.rebuildMemoPayload
import com.github.walkin.usecase.CommandPublish
import com.github.walkin.usecase.UseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class CreateMemoUsecase(
  private val commandPublish: CommandPublish,
  private val userQuery: UserQuery,
  private val workspaceSettingQuery: WorkspaceSettingQuery,
  private val database: R2dbcDatabase,
) : UseCase<CreateMemo, Memo>() {
  @OptIn(ExperimentalUuidApi::class)
  override suspend fun handle(command: CreateMemo): Memo {
    val requestUser = userQuery.getCurrentRequestOwner()

    var create =
      Memo(content = command.content, creator = requestUser.id, uid = Uuid.random().toString())
        .apply { command.visibility?.let { visibility = it } }

    val workspaceMemoRelatedSetting = workspaceSettingQuery.getWorkspaceMemoRelatedSetting()

    if (
      workspaceMemoRelatedSetting.disallowPublicVisibility &&
        create.visibility == MemosVisibility.PUBLIC
    ) {
      throw MemosExceptionFactory.Memo.publicVisibilityDenied()
    }

    if (create.content.length > workspaceMemoRelatedSetting.contentLengthLimit) {
      throw MemosExceptionFactory.Memo.contentMaxLimitDenied(
        workspaceMemoRelatedSetting.contentLengthLimit
      )
    }

    create.rebuildMemoPayload()

    create = database.runQuery { QueryDsl.insert(Entity.memo).single(create) }

    if (command.resources.isNotEmpty()) {
      commandPublish.command(SetMemoResources(create.uid, command.resources))
    }

    if (command.relations.isNotEmpty()) {
      commandPublish.command(SetMemoRelations(create.uid, command.relations))
    }

    // todo dispatch webhook

    return create
  }
}
