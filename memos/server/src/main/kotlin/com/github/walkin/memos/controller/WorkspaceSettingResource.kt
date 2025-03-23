package com.github.walkin.memos.controller

import com.github.walkin.memos.MemosController
import com.github.walkin.memos.domain.*
import com.github.walkin.memos.query.GlobalSettingQuery
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.usecase.CommandPublish
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.info.BuildProperties
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@MemosController
class WorkspaceSettingResource(
  @Value("\${app.mode}") private val appMode: String,
  private val userQuery: UserQuery,
  private val buildProperties: BuildProperties,
  private val commandPublish: CommandPublish,
  private val globalSettingQuery: GlobalSettingQuery,
) {

  @GetMapping("/workspace/profile")
  suspend fun getWorkspaceProfile(): ResponseEntity<GlobalProfile> {

    val owner = userQuery.getInstanceOwner()?.id.toString()

    val profile =
      GlobalProfile(
        version = buildProperties.version,
        owner = owner,
        mode = appMode,
        instanceUrl = "",
      )

    return ResponseEntity.ok(profile)
  }

  //  @GetMapping("/workspace/{name}")
  //  suspend fun getWorkspaceSettings(
  //    @PathVariable("name") name: String
  //  ): ResponseEntity<WorkspaceSettingResponse> {
  //    return globalSettingQuery
  //      .getWorkspaceSetting(name)
  //      .let {
  //        WorkspaceSettingResponse(name).apply {
  //          when (it) {
  //            is GlobalBasicSetting -> TODO()
  //            is GlobalGeneralSetting -> generalSetting = it
  //            is GlobalMemoRelatedSetting -> memoRelatedSetting = it
  //            is GlobalStorageSetting -> storageSetting = it
  //          }
  //        }
  //      }
  //      .let { ResponseEntity.ok(it) }
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
