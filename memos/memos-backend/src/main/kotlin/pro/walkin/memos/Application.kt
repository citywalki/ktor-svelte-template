package pro.walkin.memos

import io.ktor.server.application.Application
import pro.walkin.memos.configure.configureDatabase
import pro.walkin.memos.configure.configureDependencies
import pro.walkin.memos.configure.configureDevWeb
import pro.walkin.memos.configure.configureWeb

fun main(args: Array<String>) {
    io.ktor.server.cio.EngineMain.main(args)
}

suspend fun Application.module() {
    configureWeb()
    configureDevWeb()
    configureDatabase()
    configureDependencies()
}
