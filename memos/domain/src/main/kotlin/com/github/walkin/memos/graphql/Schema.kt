package com.github.walkin.memos.graphql

import com.github.walkin.memos.domain.GlobalGeneralSetting
import com.github.walkin.memos.domain.GlobalMemoRelatedSetting
import com.github.walkin.memos.domain.GlobalStorageSetting
import kotlinx.serialization.Serializable

@Serializable
data class GlobalSettings(
  val generalSetting: GlobalGeneralSetting? = null,
  val storageSetting: GlobalStorageSetting? = null,
  val memoRelatedSetting: GlobalMemoRelatedSetting? = null,
)
