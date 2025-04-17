package entity.types

import domain.SystemSetting
import io.r2dbc.postgresql.codec.Json
import io.r2dbc.spi.Row
import io.r2dbc.spi.Statement
import org.komapper.r2dbc.spi.R2dbcUserDefinedDataType
import kotlin.reflect.typeOf

class SystemSettingType : R2dbcUserDefinedDataType<SystemSetting> {
    val jsonUtil = kotlinx.serialization.json.Json { }
    override val name: String
        get() = "jsonb"
    override val type = typeOf<SystemSetting>()

    override val r2dbcType: Class<Json> = Json::class.javaObjectType

    override fun getValue(row: Row, index: Int): SystemSetting? {
        return getValue(row.get(index))
    }

    override fun getValue(row: Row, columnLabel: String): SystemSetting? {

        return getValue(row.get(columnLabel))
    }

    fun getValue(value: Any?): SystemSetting? {
        return value?.let {
            if (value is Json) {
                jsonUtil.decodeFromString<SystemSetting>(value.asString())
            } else {
                null
            }
        }
    }

    override fun setValue(statement: Statement, index: Int, value: SystemSetting) {
        statement.bind(index, getValue(value))
    }

    override fun setValue(statement: Statement, name: String, value: SystemSetting) {
        statement.bind(name, getValue(value))
    }

    private fun getValue(value: SystemSetting): Json {
        return Json.of(jsonUtil.encodeToString(value))
    }

    override fun toString(value: SystemSetting): String {
        return jsonUtil.encodeToString(value)
    }

}
