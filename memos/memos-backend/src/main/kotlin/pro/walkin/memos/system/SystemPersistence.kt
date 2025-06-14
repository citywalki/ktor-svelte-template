package pro.walkin.memos.system

import domain.GeneralSystemSettingDetail
import domain.SystemSettingDetail
import domain.SystemSettingKey
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.map
import org.komapper.core.dsl.query.singleOrNull

@KomapperEntity(aliases = ["systemSetting"])
@KomapperTable("system_setting")
data class SystemSettingDef(
    @KomapperId val key: SystemSettingKey,

    val value: SystemSettingDetail,
)

object SystemSettingDAO {
    private fun findSystemSetting(key: SystemSettingKey) = QueryDsl.from(Meta.systemSetting).where {
        Meta.systemSetting.key eq key
    }.singleOrNull()

    fun findGeneralSystemSetting() = findSystemSetting(SystemSettingKey.GENERAL).map {
        it?.let {
            it.value as GeneralSystemSettingDetail
        } ?: GeneralSystemSettingDetail()
    }
}
