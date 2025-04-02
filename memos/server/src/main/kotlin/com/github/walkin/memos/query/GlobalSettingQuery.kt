package com.github.walkin.memos.query

import com.github.walkin.memos.MemosExceptionFactory
import com.github.walkin.memos.domain.*
import com.github.walkin.memos.entity.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GlobalSettingQuery(private val userQuery: UserQuery) {

  fun getWorkspaceMemoRelatedSetting(): MemoRelatedGlobalSetting {
    TODO()
  }

  fun getWorkspaceGeneralSetting(): GeneralGlobalSetting =
    getWorkspaceSetting(GlobalSettingKey.GENERAL) as GeneralGlobalSetting

  fun getGlobalMemoRelatedSetting(): MemoRelatedGlobalSetting =
    getWorkspaceSetting(GlobalSettingKey.MEMO_RELATED) as MemoRelatedGlobalSetting

  fun getWorkspaceSetting(name: GlobalSettingKey): GlobalSetting {

    val workspaceSetting =
      SystemSettingEntity.find { SystemSettingTable.id eq name }
        .singleOrNull()
        .let {
          when (name) {
            GlobalSettingKey.WORKSPACE_SETTING_KEY_UNSPECIFIED ->
              throw IllegalStateException("unsupported workspace setting key: $name")
            GlobalSettingKey.BASIC -> convertBasicSettingFromRaw(it)
            GlobalSettingKey.GENERAL -> convertGeneralSettingFromRaw(it)
            GlobalSettingKey.STORAGE -> StorageGlobalSetting()
            GlobalSettingKey.MEMO_RELATED -> convertRelatedSettingFromRaw(it)
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
    systemSetting: SystemSettingEntity?
  ): MemoRelatedGlobalSetting {
    return systemSetting?.let { MemoRelatedGlobalSetting() } ?: MemoRelatedGlobalSetting()
  }

  private fun convertGeneralSettingFromRaw(
    systemSetting: SystemSettingEntity?
  ): GeneralGlobalSetting {
    return systemSetting?.let { GeneralGlobalSetting() } ?: GeneralGlobalSetting()
  }

  private fun convertBasicSettingFromRaw(systemSetting: SystemSettingEntity?): BasicGlobalSetting {
    return systemSetting?.let { BasicGlobalSetting() } ?: BasicGlobalSetting(secretKey = "ddd")
  }
}
