package com.github.walkin.memos.graphql

import com.github.walkin.memos.domain.GlobalGeneralSetting
import com.github.walkin.memos.domain.GlobalMemoRelatedSetting
import com.github.walkin.memos.domain.GlobalProfile
import com.github.walkin.memos.domain.GlobalStorageSetting
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
    val owner = userQuery.getInstanceOwner()?.id.toString()

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
  suspend fun generalSetting(): GlobalGeneralSetting {
    return globalSettingQuery.getWorkspaceGeneralSetting()
  }

  @SchemaMapping(typeName = "GlobalSettings")
  suspend fun memoRelatedSetting(): GlobalMemoRelatedSetting {
    return GlobalMemoRelatedSetting()
  }

  @SchemaMapping(typeName = "GlobalSettings")
  suspend fun storageSetting(): GlobalStorageSetting {
    return GlobalStorageSetting()
  }
}
