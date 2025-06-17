package pro.walkin.memos.configure

import io.ktor.server.application.Application
import io.ktor.server.config.property
import io.ktor.server.config.propertyOrNull
import io.ktor.server.plugins.di.dependencies
import io.r2dbc.spi.ConnectionFactoryOptions
import org.komapper.r2dbc.R2dbcDatabase

fun Application.configureDatabase() {
    val url = property<String>("database.url")
    val dbUser = propertyOrNull<String>("database.user")
    val dbPassword = propertyOrNull<String>("database.password")
    val options = ConnectionFactoryOptions.builder()
        .from(ConnectionFactoryOptions.parse(url))
        .apply {
            dbUser?.let { user -> option(ConnectionFactoryOptions.USER, user) }
            dbPassword?.let { pwd -> option(ConnectionFactoryOptions.PASSWORD, pwd) }
        }
        .build()

    dependencies {
        provide { R2dbcDatabase(options) }
    }
}
