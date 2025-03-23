package com.github.walkin.memos.query

import com.github.walkin.memos.Entity
import com.github.walkin.memos.MemosExceptionFactory
import com.github.walkin.memos.domain.*
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.map
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GlobalSettingQuery(private val database: R2dbcDatabase, private val userQuery: UserQuery) {

  suspend fun getWorkspaceMemoRelatedSetting(): GlobalMemoRelatedSetting {
    TODO()
  }

  suspend fun getWorkspaceGeneralSetting(): GlobalGeneralSetting =
    getWorkspaceSetting(GlobalSettingKey.GENERAL) as GlobalGeneralSetting

  suspend fun getGlobalMemoRelatedSetting(): GlobalMemoRelatedSetting =
    getWorkspaceSetting(GlobalSettingKey.MEMO_RELATED) as GlobalMemoRelatedSetting

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
              GlobalSettingKey.STORAGE -> GlobalStorageSetting()
              GlobalSettingKey.MEMO_RELATED -> convertRelatedSettingFromRaw(workspaceSetting)
            }
          }
      }

    // For storage setting, only host can get it.
    if (workspaceSetting.key == GlobalSettingKey.STORAGE) {

      val user = userQuery.getCurrentRequestOwner()

      if (user.role != UserRole.HOST) {
        throw MemosExceptionFactory.permissionDenied()
      }
    }

    return workspaceSetting
  }

  private fun convertRelatedSettingFromRaw(
    workspaceSetting: GlobalSettingEntity?
  ): GlobalMemoRelatedSetting {
    return workspaceSetting?.let { setting -> GlobalMemoRelatedSetting() }
      ?: GlobalMemoRelatedSetting()
  }

  private fun convertGeneralSettingFromRaw(
    workspaceSetting: GlobalSettingEntity?
  ): GlobalGeneralSetting {
    return workspaceSetting?.let { GlobalGeneralSetting() } ?: GlobalGeneralSetting()
  }

  private fun convertBasicSettingFromRaw(
    workspaceSetting: GlobalSettingEntity?
  ): GlobalBasicSetting {
    return workspaceSetting?.let { GlobalBasicSetting() } ?: GlobalBasicSetting(secretKey = "ddd")
  }
}
