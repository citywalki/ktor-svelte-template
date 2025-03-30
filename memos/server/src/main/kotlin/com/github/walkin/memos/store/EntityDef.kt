package com.github.walkin.memos.store

import com.github.walkin.memos.domain.GlobalSettingKey
import kotlinx.datetime.LocalDateTime
import org.komapper.annotation.*

@KomapperEntity
@KomapperTable("system_setting")
data class SystemSetting(
  @KomapperVersion val version: Int = 0,
  @KomapperCreatedAt val createdAt: LocalDateTime,
  @KomapperUpdatedAt val updatedAt: LocalDateTime,
  @KomapperId val name: GlobalSettingKey = GlobalSettingKey.WORKSPACE_SETTING_KEY_UNSPECIFIED,
  var value: String,
)
