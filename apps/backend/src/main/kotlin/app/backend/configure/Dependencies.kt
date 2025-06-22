package app.backend.configure

import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies
import app.backend.domain.auth.AuthService
import app.backend.domain.system.persistence.SystemSettingDAOFacadeKomapper
import app.backend.domain.user.persistence.UserDAOKomapper

fun Application.configureDependencies() {
    dependencies {
        provide { UserDAOKomapper(resolve()) }
        provide { SystemSettingDAOFacadeKomapper(resolve()) }
        provide {
            AuthService(
                resolve(),
                resolve(),
                resolve(),
            )
        }
    }
}
