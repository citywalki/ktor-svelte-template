package pro.walkin.memos.configure

import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopped
import io.ktor.server.config.property
import io.ktor.server.config.propertyOrNull
import io.ktor.server.plugins.di.dependencies
import io.r2dbc.spi.ConnectionFactoryOptions
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.PostgreSQLContainer.POSTGRESQL_PORT
import org.testcontainers.containers.PostgreSQLR2DBCDatabaseContainer
import org.testcontainers.utility.DockerImageName
import pro.walkin.memos.domain.system.persistence.systemSetting
import pro.walkin.memos.domain.user.persistence.user

suspend fun Application.configureDatabase() {
    val options = if (developmentMode) {
        val pgsql = PostgreSQLContainer(
            DockerImageName.parse("mjquinlan2000/postgis:16-3.4-alpine")
                .asCompatibleSubstituteFor("postgres")
        )
        pgsql.start()

        monitor.subscribe(ApplicationStopped) {
            pgsql.stop()
        }

        val user = pgsql.username
        val password = pgsql.password
        val url = "r2dbc:postgresql://${pgsql.host}:${pgsql.getMappedPort(POSTGRESQL_PORT)}/${pgsql.databaseName}"

        PostgreSQLR2DBCDatabaseContainer.getOptions(pgsql)
    } else {
        val url = property<String>("database.url")
        val dbUser = propertyOrNull<String>("database.user")
        val dbPassword = propertyOrNull<String>("database.password")
        ConnectionFactoryOptions.builder()
            .from(ConnectionFactoryOptions.parse(url))
            .apply {
                dbUser?.let { user -> option(ConnectionFactoryOptions.USER, user) }
                dbPassword?.let { pwd -> option(ConnectionFactoryOptions.PASSWORD, pwd) }
            }
            .build()
    }

    val db = R2dbcDatabase(options)
    db.withTransaction {
        db.runQuery {
            QueryDsl.create(Meta.systemSetting)
        }
        db.runQuery {
            QueryDsl.create(Meta.user)
        }
    }
    dependencies {
        provide { db }
    }
}
