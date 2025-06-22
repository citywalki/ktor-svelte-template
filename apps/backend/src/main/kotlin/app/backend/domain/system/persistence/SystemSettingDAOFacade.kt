package app.backend.domain.system.persistence

import model.GeneralSystemSettingDetail

interface SystemSettingDAOFacade {

    suspend fun findGeneralSystemSetting(): GeneralSystemSettingDetail
}
