package pro.walkin.memos.domain.system.persistence

import domain.GeneralSystemSettingDetail
import domain.SystemSettingKey
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.map
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase

class SystemSettingDAOFacadeKomapper(
    private val db: R2dbcDatabase
) : SystemSettingDAOFacade {
    private val ss = Meta.systemSetting

    override suspend fun findGeneralSystemSetting(): GeneralSystemSettingDetail {
        val query = QueryDsl.from(ss).where {
            ss.key eq SystemSettingKey.GENERAL
        }.singleOrNull().map {
            it?.let {
                it.value as GeneralSystemSettingDetail
            } ?: GeneralSystemSettingDetail()
        }

        return db.runQuery(query)
    }
}
