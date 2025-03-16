package com.github.walkin.memos.domain

import com.github.walkin.memos.entity.WorkspaceGeneralSetting
import com.github.walkin.memos.entity.WorkspaceMemoRelatedSetting
import com.github.walkin.memos.entity.WorkspaceSetting
import com.github.walkin.memos.entity.WorkspaceStorageSetting
import com.github.walkin.usecase.Command
import kotlinx.serialization.Serializable

@Serializable
data class SetWorkspaceSetting(val workspaceSetting: WorkspaceSetting) :
  Command<WorkspaceSetting>()

@Serializable
data class UpdateWorkspaceSettingRequest(
  val generalSetting: WorkspaceGeneralSetting? = null,
  val storageSetting: WorkspaceStorageSetting? = null,
  val memoRelatedSetting: WorkspaceMemoRelatedSetting? = null,
)

@Serializable
data class WorkspaceProfile(
  // The name of instance owner.
  // Format: users/{user}
  val owner: String,
  // version is the current version of instance
  val version: String,
  // mode is the instance mode (e.g. "prod", "dev" or "demo").
  val mode: String,
  val instanceUrl: String,
)

@Serializable
data class WorkspaceSettingResponse(
  var name: String,
  var generalSetting: WorkspaceGeneralSetting? = null,
  var storageSetting: WorkspaceStorageSetting? = null,
  var memoRelatedSetting: WorkspaceMemoRelatedSetting? = null,
)
