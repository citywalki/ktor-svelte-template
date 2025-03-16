package com.github.walkin.memos.controller

import com.github.walkin.memos.MemosController
import com.github.walkin.memos.domain.SetWorkspaceSetting
import com.github.walkin.memos.domain.UpdateWorkspaceSettingRequest
import com.github.walkin.memos.domain.WorkspaceProfile
import com.github.walkin.memos.domain.WorkspaceSettingResponse
import com.github.walkin.memos.entity.*
import com.github.walkin.memos.query.UserQuery
import com.github.walkin.memos.query.WorkspaceSettingQuery
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
  private val workspaceSettingQuery: WorkspaceSettingQuery,
) {

  @GetMapping("/workspace/profile")
  suspend fun getWorkspaceProfile(): ResponseEntity<WorkspaceProfile> {

    val owner = userQuery.getInstanceOwner()?.id.toString()

    val profile =
      WorkspaceProfile(
        version = buildProperties.version,
        owner = owner,
        mode = appMode,
        instanceUrl = "",
      )

    return ResponseEntity.ok(profile)
  }

  @GetMapping("/workspace/{name}")
  suspend fun getWorkspaceSettings(
    @PathVariable("name") name: String
  ): ResponseEntity<WorkspaceSettingResponse> {
    return workspaceSettingQuery
      .getWorkspaceSetting(name)
      .let {
        WorkspaceSettingResponse(name).apply {
          when (it) {
            is WorkspaceBasicSetting -> TODO()
            is WorkspaceGeneralSetting -> generalSetting = it
            is WorkspaceMemoRelatedSetting -> memoRelatedSetting = it
            is WorkspaceStorageSetting -> storageSetting = it
          }
        }
      }
      .let { ResponseEntity.ok(it) }
  }

  @PatchMapping("/workspace/{name}")
  suspend fun updateWorkspaceSettings(
    @PathVariable("name") settingName: WorkspaceSettingKey,
    @RequestBody updateWorkspaceSettingRequest: UpdateWorkspaceSettingRequest,
  ) {

    val setting =
      when (settingName) {
        WorkspaceSettingKey.GENERAL -> updateWorkspaceSettingRequest.generalSetting
        WorkspaceSettingKey.STORAGE -> updateWorkspaceSettingRequest.storageSetting
        WorkspaceSettingKey.MEMO_RELATED -> updateWorkspaceSettingRequest.memoRelatedSetting
        else -> throw NotImplementedError()
      }
    setting?.let { commandPublish.command(SetWorkspaceSetting(it)) }
  }
}
