package com.github.walkin.memos.query

import com.github.walkin.memos.Entity
import com.github.walkin.memos.MemosExceptionFactory
import com.github.walkin.memos.domain.UserRole
import com.github.walkin.memos.entity.*
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.map
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class WorkspaceSettingQuery(private val database: R2dbcDatabase, private val userQuery: UserQuery) {

  suspend fun getWorkspaceMemoRelatedSetting(): WorkspaceMemoRelatedSetting {
    TODO()
  }

  suspend fun getWorkspaceGeneralSetting(): WorkspaceGeneralSetting =
    getWorkspaceSetting(WorkspaceSettingKey.GENERAL) as WorkspaceGeneralSetting

  suspend fun getWorkspaceSetting(name: String): WorkspaceSetting {
    val workspaceSettingKey = WorkspaceSettingKey.valueOf(name.uppercase())
    return getWorkspaceSetting(workspaceSettingKey)
  }

  private suspend fun getWorkspaceSetting(name: WorkspaceSettingKey): WorkspaceSetting {
    val workspaceSetting =
      database.runQuery {
        QueryDsl.Companion.from(Entity.workspaceSetting)
          .where { Entity.workspaceSetting.name eq name }
          .singleOrNull()
          .map { workspaceSetting ->
            when (name) {
              WorkspaceSettingKey.WORKSPACE_SETTING_KEY_UNSPECIFIED ->
                throw IllegalStateException("unsupported workspace setting key: $name")
              WorkspaceSettingKey.BASIC -> convertBasicSettingFromRaw(workspaceSetting)
              WorkspaceSettingKey.GENERAL -> convertGeneralSettingFromRaw(workspaceSetting)
              WorkspaceSettingKey.STORAGE -> WorkspaceStorageSetting()
              WorkspaceSettingKey.MEMO_RELATED -> convertRelatedSettingFromRaw(workspaceSetting)
            }
          }
      }

    // For storage setting, only host can get it.
    if (workspaceSetting.key == WorkspaceSettingKey.STORAGE) {

      val user = userQuery.getCurrentRequestOwner()

      if (user.role != UserRole.HOST) {
        throw MemosExceptionFactory.permissionDenied()
      }
    }

    return workspaceSetting
  }

  private fun convertRelatedSettingFromRaw(
    workspaceSetting: WorkspaceSettingEntity?
  ): WorkspaceMemoRelatedSetting {
    return workspaceSetting?.let { setting -> WorkspaceMemoRelatedSetting() }
      ?: WorkspaceMemoRelatedSetting()
  }

  private fun convertGeneralSettingFromRaw(
    workspaceSetting: WorkspaceSettingEntity?
  ): WorkspaceGeneralSetting {
    return workspaceSetting?.let { WorkspaceGeneralSetting() } ?: WorkspaceGeneralSetting()
  }

  private fun convertBasicSettingFromRaw(
    workspaceSetting: WorkspaceSettingEntity?
  ): WorkspaceBasicSetting {
    return workspaceSetting?.let { setting -> WorkspaceBasicSetting() }
      ?: WorkspaceBasicSetting(secretKey = "ddd")
  }
}
