package pro.walkin.memo.config

import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import query.Query
import service.Service

fun Application.configureFrameworks() {
//  install(Koin) {
//    slf4jLogger()
//    modules(
//      module {
//        single<Query> { object : Query {} }
//        single<Service> { object : Service(get()) {} }
//      }
//    )
//  }
}
