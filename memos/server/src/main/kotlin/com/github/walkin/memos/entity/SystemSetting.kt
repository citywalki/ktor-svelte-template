package com.github.walkin.memos.entity

import com.github.walkin.memos.domain.GeneralGlobalSetting
import com.github.walkin.memos.domain.GlobalSettingKey
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object SystemSettingTable : BaseIdTable<GlobalSettingKey>("system_setting") {
  override val id = enumerationByName("key", 20, GlobalSettingKey::class).entityId()

  val value = text("value")
}

class SystemSettingEntity(id: EntityID<GlobalSettingKey>) :
  BaseEntity<GlobalSettingKey>(id, SystemSettingTable) {
  companion object : BaseEntityClass<GlobalSettingKey, SystemSettingEntity>(SystemSettingTable) {

    fun findGeneralSetting(): GeneralGlobalSetting {
      return find(SystemSettingTable.id.eq(GlobalSettingKey.GENERAL)).singleOrNull()?.let {
        globalSetting ->
        GeneralGlobalSetting()
      } ?: GeneralGlobalSetting()
    }
  }

  val key by SystemSettingTable.id
  val value by SystemSettingTable.value
}
