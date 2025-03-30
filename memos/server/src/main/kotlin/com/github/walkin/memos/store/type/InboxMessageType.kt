package com.github.walkin.memos.store.type

import com.github.walkin.memos.entity.InboxMessage
import io.r2dbc.spi.Row
import io.r2dbc.spi.Statement
import kotlin.reflect.KType
import kotlin.reflect.typeOf
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.komapper.r2dbc.spi.R2dbcUserDefinedDataType

class InboxMessageType : R2dbcUserDefinedDataType<InboxMessage> {
  override val name: String = "text"
  override val r2dbcType: Class<String> = String::class.javaObjectType
  override val type: KType = typeOf<InboxMessage>()

  override fun getValue(row: Row, index: Int): InboxMessage? {
    return row.get(index, String::class.java)?.let { Json.decodeFromString<InboxMessage>(it) }
  }

  override fun getValue(row: Row, columnLabel: String): InboxMessage? {
    return row.get(columnLabel, String::class.java)?.let { Json.decodeFromString<InboxMessage>(it) }
  }

  override fun toString(value: InboxMessage): String {
    return Json.encodeToString(value)
  }

  override fun setValue(statement: Statement, name: String, value: InboxMessage) {
    statement.bind(name, Json.encodeToString(value))
  }

  override fun setValue(statement: Statement, index: Int, value: InboxMessage) {
    statement.bind(index, Json.encodeToString(value))
  }
}
