package pro.walkin.memos.configure

import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies
import pro.walkin.memos.domain.auth.AuthService
import pro.walkin.memos.domain.system.persistence.SystemSettingDAOFacadeKomapper
import pro.walkin.memos.domain.user.persistence.UserDAOKomapper

fun Application.configureDependencies() {
    dependencies {
        provide { UserDAOKomapper(resolve()) }
        provide { SystemSettingDAOFacadeKomapper(resolve()) }
        provide {
            AuthService(
                resolve(),
                resolve(),
                resolve(),
                resolve()
            )
        }
    }
}
