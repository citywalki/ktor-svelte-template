package com.github.walkin.memos.query

import com.github.walkin.memos.Entity
import com.github.walkin.memos.MemosExceptionFactory
import com.github.walkin.memos.domain.*
import com.github.walkin.memos.entity.*
import com.github.walkin.memos.store.SystemSetting
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.map
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GlobalSettingQuery(private val database: R2dbcDatabase, private val userQuery: UserQuery) {

  suspend fun getWorkspaceMemoRelatedSetting(): MemoRelatedGlobalSetting {
    TODO()
  }

  suspend fun getWorkspaceGeneralSetting(): GeneralGlobalSetting =
    getWorkspaceSetting(GlobalSettingKey.GENERAL) as GeneralGlobalSetting

  suspend fun getGlobalMemoRelatedSetting(): MemoRelatedGlobalSetting =
    getWorkspaceSetting(GlobalSettingKey.MEMO_RELATED) as MemoRelatedGlobalSetting

  suspend fun getWorkspaceSetting(name: GlobalSettingKey): GlobalSetting {
    val workspaceSetting =
      database.runQuery {
        QueryDsl.Companion.from(Entity.workspaceSetting)
          .where { Entity.workspaceSetting.name eq name }
          .singleOrNull()
          .map { workspaceSetting ->
            when (name) {
              GlobalSettingKey.WORKSPACE_SETTING_KEY_UNSPECIFIED ->
                throw IllegalStateException("unsupported workspace setting key: $name")
              GlobalSettingKey.BASIC -> convertBasicSettingFromRaw(workspaceSetting)
              GlobalSettingKey.GENERAL -> convertGeneralSettingFromRaw(workspaceSetting)
              GlobalSettingKey.STORAGE -> StorageGlobalSetting()
              GlobalSettingKey.MEMO_RELATED -> convertRelatedSettingFromRaw(workspaceSetting)
            }
          }
      }

    // For storage setting, only host can get it.
    if (workspaceSetting.key == GlobalSettingKey.STORAGE) {

      val user = userQuery.getCurrentRequestOwner()

      if (user?.role != UserRole.HOST) {
        throw MemosExceptionFactory.permissionDenied()
      }
    }

    return workspaceSetting
  }

  private fun convertRelatedSettingFromRaw(
    systemSetting: SystemSetting?
  ): MemoRelatedGlobalSetting {
    return systemSetting?.let { MemoRelatedGlobalSetting() } ?: MemoRelatedGlobalSetting()
  }

  private fun convertGeneralSettingFromRaw(systemSetting: SystemSetting?): GeneralGlobalSetting {
    return systemSetting?.let { GeneralGlobalSetting() } ?: GeneralGlobalSetting()
  }

  private fun convertBasicSettingFromRaw(systemSetting: SystemSetting?): BasicGlobalSetting {
    return systemSetting?.let { BasicGlobalSetting() } ?: BasicGlobalSetting(secretKey = "ddd")
  }
}
