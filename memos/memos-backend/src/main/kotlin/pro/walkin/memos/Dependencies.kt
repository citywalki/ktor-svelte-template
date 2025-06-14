package pro.walkin.memos

import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.plugins.di.*
import org.komapper.jdbc.JdbcDatabase
import pro.walkin.memos.auth.AuthService
import pro.walkin.memos.user.UserQuery

fun Application.dependencies() {
    val url = property<String>("database.url")
    dependencies {
        provide { JdbcDatabase(url) }
        provide { UserQuery(resolve()) }
        provide { AuthService(resolve()) }
    }
}
