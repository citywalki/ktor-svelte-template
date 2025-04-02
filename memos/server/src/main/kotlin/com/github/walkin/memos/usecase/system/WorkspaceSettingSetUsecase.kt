package com.github.walkin.memos.usecase.system

import com.github.walkin.memos.MemosExceptionFactory
import com.github.walkin.memos.domain.SetGlobalSetting
import com.github.walkin.memos.entity.UserRole
import com.github.walkin.memos.query.GlobalSettingQuery
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.usecase.UseCase
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.springframework.stereotype.Service

@Service
class WorkspaceSettingSetUsecase(
  private val userQuery: UserQuery,
  private val globalSettingQuery: GlobalSettingQuery,
) : UseCase<SetGlobalSetting, Unit>() {

  @OptIn(ExperimentalSerializationApi::class)
  override fun handle(command: SetGlobalSetting) {
    val setting = command.globalSetting

    if (userQuery.getCurrentRequestOwner()?.role != UserRole.HOST) {
      throw MemosExceptionFactory.permissionDenied()
    }

    val valueBytes = ProtoBuf.encodeToByteArray(setting).toString()

    //    database.runQuery {
    //      QueryDsl.from(Entity.workspaceSetting)
    //        .where { Entity.workspaceSetting.name eq setting.key }
    //        .single()
    //        .flatMap {
    //          it.value = valueBytes
    //          QueryDsl.insert(Entity.workspaceSetting).single(it)
    //        }
    //    }

  }

  override fun getCommandType() = SetGlobalSetting::class
}
