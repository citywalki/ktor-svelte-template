package query

import domain.BasicSystemSetting
import domain.GeneralSystemSetting
import domain.MemoRelatedSystemSetting
import domain.StorageSystemSetting
import domain.SystemSetting
import domain.SystemSettingKey
import entity.systemSetting
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.singleOrNull

private object SystemSettingPatters{

}

val systemSetting = Meta.systemSetting

suspend fun Query.findSystemSetting(key: SystemSettingKey) = database.runQuery {
  QueryDsl.from(systemSetting)
    .where { systemSetting.key eq key }.singleOrNull()
}?.let {
  when(key){
    SystemSettingKey.WORKSPACE_SETTING_KEY_UNSPECIFIED -> TODO()
    SystemSettingKey.BASIC -> BasicSystemSetting()
    SystemSettingKey.GENERAL -> GeneralSystemSetting()
    SystemSettingKey.STORAGE -> StorageSystemSetting()
    SystemSettingKey.MEMO_RELATED -> MemoRelatedSystemSetting()
  }
}

suspend fun Query.findGeneralSystemSetting(): GeneralSystemSetting =
  findSystemSetting(SystemSettingKey.GENERAL) as GeneralSystemSetting? ?: GeneralSystemSetting()
