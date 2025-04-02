package com.github.walkin.memos.usecase.memo

import com.github.walkin.memos.domain.*


// @Component
// @Transactional
// class CreateMemoUsecase(
//  private val commandPublish: CommandPublish,
//  private val userQuery: UserQuery,
//  private val globalSettingQuery: GlobalSettingQuery,
// ) : UseCase<CreateMemo, Memo>() {
//  @OptIn(ExperimentalUuidApi::class)
//  override suspend fun handle(command: CreateMemo): EntityID {
//    val requestUser =
//      userQuery.getCurrentRequestOwner() ?: throw MemosExceptionFactory.permissionDenied()
//
//    var create =
//      Memo(content = command.content, creator = requestUser.id).apply {
//        command.visibility?.let { visibility = it }
//      }
//
//    val workspaceMemoRelatedSetting = globalSettingQuery.getWorkspaceMemoRelatedSetting()
//
//    if (
//      workspaceMemoRelatedSetting.disallowPublicVisibility &&
//        create.visibility == MemosVisibility.PUBLIC
//    ) {
//      throw MemosExceptionFactory.MemoExceptions.publicVisibilityDenied()
//    }
//
//    if (create.content.length > workspaceMemoRelatedSetting.contentLengthLimit) {
//      throw MemosExceptionFactory.MemoExceptions.contentMaxLimitDenied(
//        workspaceMemoRelatedSetting.contentLengthLimit
//      )
//    }
//
//    create.rebuildMemoPayload()
//
//    create = database.runQuery { QueryDsl.insert(Entity.memo).single(create) }
//
//    if (command.resources.isNotEmpty()) {
//      commandPublish.command(SetMemoResources(create.uid, command.resources))
//    }
//
//    if (command.relations.isNotEmpty()) {
//      commandPublish.command(SetMemoRelations(create.uid, command.relations))
//    }
//
//    // todo dispatch webhook
//
//    return create.uid
//  }
// }
