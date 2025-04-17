package pro.walkin.memo

import domain.GeneralSystemSetting
import domain.SystemSettingKey
import entity.SystemSettingDef
import entity.systemSetting
import entity.user
import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import pro.walkin.memo.config.configureDatabases
import pro.walkin.memo.config.configureMonitoring
import pro.walkin.memo.config.configureRouting
import pro.walkin.memo.config.configureSecurity
import query.Query
import service.Service

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureMonitoring()
    val database = configureDatabases()

    CoroutineScope(Dispatchers.IO).launch {
        database.runQuery {
            QueryDsl.create(Meta.user)
        }
        database.runQuery {
            QueryDsl.create(Meta.systemSetting)
        }

    }

    val query = object : Query(database) {}
    val service: Service = object : Service(query, database) {}

    configureRouting(query, service)
}
