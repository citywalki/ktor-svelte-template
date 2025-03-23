package com.github.walkin.memos.usecase.system

import com.github.walkin.memos.Entity
import com.github.walkin.memos.MemosExceptionFactory
import com.github.walkin.memos.domain.GlobalSetting
import com.github.walkin.memos.domain.SetGlobalSetting
import com.github.walkin.memos.domain.UserRole
import com.github.walkin.memos.query.GlobalSettingQuery
import com.github.walkin.memos.query.UserQuery
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
  private val globalSettingQuery: GlobalSettingQuery,
) : UseCase<SetGlobalSetting, GlobalSetting>() {

  @OptIn(ExperimentalSerializationApi::class)
  override suspend fun handle(command: SetGlobalSetting): GlobalSetting {
    val setting = command.globalSetting

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

    return globalSettingQuery.getWorkspaceSetting(setting.key)
  }
}
