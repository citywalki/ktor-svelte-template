package com.github.walkin.memos.controller

import com.github.walkin.memos.MemosController
import com.github.walkin.memos.domain.*
import com.github.walkin.memos.entity.*
import com.github.walkin.memos.query.GlobalSettingQuery
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.usecase.CommandPublish
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.info.BuildProperties
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@MemosController
class GlobalSettingResource(
  @Value("\${app.mode}") private val appMode: String,
  private val userQuery: UserQuery,
  private val buildProperties: BuildProperties,
  private val commandPublish: CommandPublish,
  private val globalSettingQuery: GlobalSettingQuery,
) {

  //  @GetMapping("/workspace/profile")
  //  @PermitAll
  //  suspend fun getWorkspaceProfile(): ResponseEntity<GlobalProfile> {
  //
  //    val owner = userQuery.getInstanceOwner()?.id
  //
  //    val profile =
  //      GlobalProfile(
  //        version = buildProperties.version,
  //        owner = owner,
  //        mode = appMode,
  //        instanceUrl = "",
  //      )
  //
  //    return ResponseEntity.ok(profile)
  //  }

  @PatchMapping("/workspace/{name}")
  suspend fun updateWorkspaceSettings(
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
