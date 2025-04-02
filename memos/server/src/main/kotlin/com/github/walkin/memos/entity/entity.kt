package com.github.walkin.memos.entity

import com.github.walkin.memos.domain.TableId
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

abstract class BaseIdTable<T : Any>(name: String = "") : IdTable<T>(name) {
  val created =
    datetime("created_ts").clientDefault {
      Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }
  val modified =
    datetime("updated_ts").clientDefault {
      Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }
}

open class BaseEntity<ID : Any>(id: EntityID<ID>, table: BaseIdTable<ID>) : Entity<ID>(id) {
  val created by table.created
  var modified by table.modified
}

open class BaseEntityClass<ID : Any, out T : BaseEntity<ID>>(table: BaseIdTable<ID>) :
  EntityClass<ID, T>(table) {
  init {
    EntityHook.subscribe { action ->
      if (action.changeType == EntityChangeType.Updated) {
        action.toEntity(this)?.modified =
          Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
      }
    }
  }
}

open class LongIdTable(name: String = "", columnName: String = "id") : BaseIdTable<TableId>(name) {
  final override val id: Column<EntityID<TableId>> = long(columnName).autoIncrement().entityId()
  final override val primaryKey = PrimaryKey(id)
}

open class LongIdEntity(id: EntityID<Long>, table: LongIdTable) : BaseEntity<Long>(id, table)

open class LongIdEntityClass<out T : LongIdEntity>(table: LongIdTable) :
  BaseEntityClass<Long, T>(table)
