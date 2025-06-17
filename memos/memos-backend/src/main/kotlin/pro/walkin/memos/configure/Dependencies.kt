package pro.walkin.memos.configure

import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies
import pro.walkin.memos.domain.auth.AuthService

fun Application.configureDependencies() {
    dependencies {
        provide { AuthService(resolve(), resolve(), resolve()) }
    }
}
