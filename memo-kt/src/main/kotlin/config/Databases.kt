package pro.walkin.memo.config

import io.ktor.server.application.*
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.Option
import org.komapper.r2dbc.R2dbcDatabase

fun Application.configureDatabases(): R2dbcDatabase {

  val options = ConnectionFactoryOptions.builder()
    .option(ConnectionFactoryOptions.DRIVER, "postgresql")
    .option(ConnectionFactoryOptions.HOST, "127.0.0.1")
    .option(ConnectionFactoryOptions.PORT, 13306)
    .option(ConnectionFactoryOptions.DATABASE, "memos")
    .option(Option.valueOf("DB_CLOSE_DELAY"), "-1")
    .option(ConnectionFactoryOptions.USER, "memos")
    .option(ConnectionFactoryOptions.PASSWORD, "memos")
    .build()

  return R2dbcDatabase(options)
}
