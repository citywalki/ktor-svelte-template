package app.backend.domain.system

// object SystemSettingDAO {
//    private fun findSystemSetting(key: SystemSettingKey) = QueryDsl.from(Meta.systemSetting).where {
//        Meta.systemSetting.key eq key
//    }.singleOrNull()
//
//    fun findGeneralSystemSetting() = findSystemSetting(SystemSettingKey.GENERAL).map {
//        it?.let {
//            it.value as GeneralSystemSettingDetail
//        } ?: GeneralSystemSettingDetail()
//    }
// }
