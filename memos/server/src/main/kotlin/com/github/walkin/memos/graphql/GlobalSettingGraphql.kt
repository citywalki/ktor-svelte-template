package com.github.walkin.memos.graphql

import com.github.walkin.memos.domain.GlobalProfile
import com.github.walkin.memos.entity.GeneralGlobalSetting
import com.github.walkin.memos.entity.MemoRelatedGlobalSetting
import com.github.walkin.memos.entity.StorageGlobalSetting
import com.github.walkin.memos.query.GlobalSettingQuery
import com.github.walkin.memos.query.UserQuery
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.info.BuildProperties
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class GlobalSettingGraphql(
  @Value("\${app.mode}") private val appMode: String,
  private val userQuery: UserQuery,
  private val buildProperties: BuildProperties,
  private val globalSettingQuery: GlobalSettingQuery,
) {

  @QueryMapping
  suspend fun profile(): GlobalProfile {
    val owner = userQuery.getInstanceOwner()?.id

    return GlobalProfile(
      version = buildProperties.version,
      owner = owner,
      mode = appMode,
      instanceUrl = "",
    )
  }

  @QueryMapping
  suspend fun globalSettings(): GlobalSettings {

    return GlobalSettings()
  }

  @SchemaMapping(typeName = "GlobalSettings")
  suspend fun generalSetting(): GeneralGlobalSetting {
    return globalSettingQuery.getWorkspaceGeneralSetting()
  }

  @SchemaMapping(typeName = "GlobalSettings")
  suspend fun memoRelatedSetting(): MemoRelatedGlobalSetting {
    return MemoRelatedGlobalSetting()
  }

  @SchemaMapping(typeName = "GlobalSettings")
  suspend fun storageSetting(): StorageGlobalSetting {
    return StorageGlobalSetting()
  }
}
