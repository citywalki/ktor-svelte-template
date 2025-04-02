package com.github.walkin.memos.entity

import com.github.walkin.memos.domain.GlobalSettingKey
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column

object SystemSettingTable : BaseIdTable<GlobalSettingKey>("system_setting") {
  override val id: Column<EntityID<GlobalSettingKey>> =
    enumerationByName<GlobalSettingKey>("key", 20, GlobalSettingKey::class).entityId()

  val value = text("value")
}

class SystemSettingEntity(id: EntityID<GlobalSettingKey>) :
  BaseEntity<GlobalSettingKey>(id, SystemSettingTable) {
  companion object : BaseEntityClass<GlobalSettingKey, SystemSettingEntity>(SystemSettingTable)

  val key by SystemSettingTable.id
  val value by SystemSettingTable.value
}

data class SystemSetting(
  val version: Int = 0,
  val createdAt: LocalDateTime,
  val updatedAt: LocalDateTime,
  val name: GlobalSettingKey = GlobalSettingKey.WORKSPACE_SETTING_KEY_UNSPECIFIED,
  var value: String,
)
