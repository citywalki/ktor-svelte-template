package com.github.walkin.memos.graphql

import com.github.walkin.memos.entity.GeneralGlobalSetting
import com.github.walkin.memos.entity.MemoRelatedGlobalSetting
import com.github.walkin.memos.entity.StorageGlobalSetting
import kotlinx.serialization.Serializable

@Serializable
data class GlobalSettings(
  val generalSetting: GeneralGlobalSetting? = null,
  val storageSetting: StorageGlobalSetting? = null,
  val memoRelatedSetting: MemoRelatedGlobalSetting? = null,
)
