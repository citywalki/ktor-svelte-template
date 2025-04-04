package com.github.walkin.memos.controller

import com.github.walkin.memos.domain.GlobalSettingKey
import com.github.walkin.memos.domain.SetGlobalSetting
import com.github.walkin.memos.domain.UpdateWorkspaceSettingRequest
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.usecase.CommandPublish
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.info.BuildProperties
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GlobalSettingResource(
  @Value("\${app.mode}") private val appMode: String,
  private val userQuery: UserQuery,
  private val buildProperties: BuildProperties,
  private val commandPublish: CommandPublish,
) {

  @PatchMapping("/workspace/{name}")
  fun updateWorkspaceSettings(
    @PathVariable("name") settingName: GlobalSettingKey,
    @RequestBody updateWorkspaceSettingRequest: UpdateWorkspaceSettingRequest,
  ) {

    val setting =
      when (settingName) {
        GlobalSettingKey.GENERAL -> updateWorkspaceSettingRequest.generalSetting
        GlobalSettingKey.STORAGE -> updateWorkspaceSettingRequest.storageSetting
        GlobalSettingKey.MEMO_RELATED -> updateWorkspaceSettingRequest.memoRelatedSetting
        else -> throw NotImplementedError()
      }
    setting?.let { commandPublish.command(SetGlobalSetting(it)) }
  }
}
