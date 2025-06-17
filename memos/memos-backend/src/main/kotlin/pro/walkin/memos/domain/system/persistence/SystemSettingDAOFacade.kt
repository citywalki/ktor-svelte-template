package pro.walkin.memos.domain.system.persistence

import domain.GeneralSystemSettingDetail

interface SystemSettingDAOFacade {

    suspend fun findGeneralSystemSetting(): GeneralSystemSettingDetail
}
