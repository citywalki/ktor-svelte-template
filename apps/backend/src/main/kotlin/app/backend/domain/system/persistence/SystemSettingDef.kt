package app.backend.domain.system.persistence

import model.SystemSettingDetail
import model.SystemSettingKey
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable

@KomapperEntity(aliases = ["systemSetting"])
@KomapperTable("system_setting")
data class SystemSettingDef(
    @KomapperId val key: SystemSettingKey,

    val value: SystemSettingDetail,
)
