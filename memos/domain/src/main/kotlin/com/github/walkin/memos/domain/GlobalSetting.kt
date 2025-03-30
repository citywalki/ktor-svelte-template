package com.github.walkin.memos.domain

import com.github.walkin.memos.entity.*
import com.github.walkin.usecase.Command
import kotlinx.serialization.Serializable

@Serializable
data class SetGlobalSetting(val globalSetting: GlobalSetting) : Command<GlobalSetting>()

@Serializable
data class UpdateWorkspaceSettingRequest(
  val generalSetting: GeneralGlobalSetting? = null,
  val storageSetting: StorageGlobalSetting? = null,
  val memoRelatedSetting: MemoRelatedGlobalSetting? = null,
)

@Serializable
data class GlobalProfile(
  // The name of instance owner.
  // Format: users/{user}
  val owner: EntityID? = null,
  // version is the current version of instance
  val version: String,
  // mode is the instance mode (e.g. "prod", "dev" or "demo").
  val mode: String,
  val instanceUrl: String,
)

@Serializable
data class WorkspaceSettingResponse(
  var name: String,
  var generalSetting: GeneralGlobalSetting? = null,
  var storageSetting: StorageGlobalSetting? = null,
  var memoRelatedSetting: MemoRelatedGlobalSetting? = null,
)
