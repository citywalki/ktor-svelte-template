package app.backend

import io.ktor.server.application.Application
import app.backend.configure.configureDatabase
import app.backend.configure.configureDependencies
import app.backend.configure.configureDevWeb
import app.backend.configure.configureWeb

fun main(args: Array<String>) {
    io.ktor.server.cio.EngineMain.main(args)
}

suspend fun Application.module() {
    configureWeb()
    configureDevWeb()
    configureDatabase()
    configureDependencies()
}
