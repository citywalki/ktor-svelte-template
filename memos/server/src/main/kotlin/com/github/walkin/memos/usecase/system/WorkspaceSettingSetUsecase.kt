package com.github.walkin.memos.usecase.system

import com.github.walkin.memos.Entity
import com.github.walkin.memos.MemosExceptionFactory
import com.github.walkin.memos.domain.SetWorkspaceSetting
import com.github.walkin.memos.domain.UserRole
import com.github.walkin.memos.entity.WorkspaceSetting
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.memos.query.WorkspaceSettingQuery
import com.github.walkin.usecase.UseCase
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.flatMap
import org.komapper.core.dsl.query.single
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service

@Service
class WorkspaceSettingSetUsecase(
  private val userQuery: UserQuery,
  private val database: R2dbcDatabase,
  private val workspaceSettingQuery: WorkspaceSettingQuery,
) : UseCase<SetWorkspaceSetting, WorkspaceSetting>() {

  @OptIn(ExperimentalSerializationApi::class)
  override suspend fun handle(command: SetWorkspaceSetting): WorkspaceSetting {
    val setting = command.workspaceSetting

    if (userQuery.getCurrentRequestOwner().role != UserRole.HOST) {
      throw MemosExceptionFactory.permissionDenied()
    }

    val valueBytes = ProtoBuf.encodeToByteArray(setting).toString()

    database.runQuery {
      QueryDsl.from(Entity.workspaceSetting)
        .where { Entity.workspaceSetting.name eq setting.key }
        .single()
        .flatMap {
          it.value = valueBytes
          QueryDsl.insert(Entity.workspaceSetting).single(it)
        }
    }

    return workspaceSettingQuery.getWorkspaceSetting(setting.key.name)
  }
}
