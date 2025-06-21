package pro.walkin.memos.domain.system.persistence

import domain.SystemSetting
import domain.SystemSettingDetail
import io.r2dbc.spi.Row
import io.r2dbc.spi.Statement
import kotlinx.serialization.json.Json
import org.komapper.r2dbc.spi.R2dbcUserDefinedDataType
import kotlin.reflect.typeOf

class SystemSettingType : R2dbcUserDefinedDataType<SystemSettingDetail> {
    val jsonUtil = Json { }
    override val name: String
        get() = "jsonb"
    override val type = typeOf<SystemSettingDetail>()

    override val r2dbcType: Class<io.r2dbc.postgresql.codec.Json> = io.r2dbc.postgresql.codec.Json::class.javaObjectType

    override fun getValue(row: Row, index: Int): SystemSettingDetail? {
        return getValue(row.get(index))
    }

    override fun getValue(row: Row, columnLabel: String): SystemSettingDetail? {
        return getValue(row.get(columnLabel))
    }

    fun getValue(value: Any?): SystemSettingDetail? {
        return value?.let {
            if (value is io.r2dbc.postgresql.codec.Json) {
                jsonUtil.decodeFromString<SystemSettingDetail>(value.asString())
            } else {
                null
            }
        }
    }

    override fun setValue(statement: Statement, index: Int, value: SystemSettingDetail) {
        statement.bind(index, getValue(value))
    }

    override fun setValue(statement: Statement, name: String, value: SystemSettingDetail) {
        statement.bind(name, getValue(value))
    }

    private fun getValue(value: SystemSetting): io.r2dbc.postgresql.codec.Json {
        return io.r2dbc.postgresql.codec.Json.of(jsonUtil.encodeToString(value))
    }

    override fun toString(value: SystemSettingDetail): String {
        return jsonUtil.encodeToString(value)
    }
}
