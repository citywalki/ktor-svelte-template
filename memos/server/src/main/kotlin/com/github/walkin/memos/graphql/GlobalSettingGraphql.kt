package com.github.walkin.memos.graphql

import com.github.walkin.memos.domain.*
import com.github.walkin.memos.entity.UserRole
import com.github.walkin.memos.entity.UserTable
import com.github.walkin.memos.query.GlobalSettingQuery
import com.github.walkin.memos.query.UserQuery
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.info.BuildProperties
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Serializable
data class GlobalSettings(
  val generalSetting: GeneralGlobalSetting? = null,
  val storageSetting: StorageGlobalSetting? = null,
  val memoRelatedSetting: MemoRelatedGlobalSetting? = null,
)

@Controller
class GlobalSettingGraphql(
  @Value("\${app.mode}") private val appMode: String,
  private val userQuery: UserQuery,
  private val buildProperties: BuildProperties,
  private val globalSettingQuery: GlobalSettingQuery,
) {

  @QueryMapping
  suspend fun profile(): GlobalProfile = transaction {
    val owner =
      UserTable.select(UserTable.id)
        .where { UserTable.role eq UserRole.HOST }
        .singleOrNull()
        ?.let { it[UserTable.id].value }

    GlobalProfile(
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
