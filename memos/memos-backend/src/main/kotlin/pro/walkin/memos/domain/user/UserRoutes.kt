package pro.walkin.memos.domain.user

import io.ktor.server.application.Application
import io.ktor.server.application.log
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.application
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.slf4j.MDC

suspend fun Application.userRoutes() {
    routing {
        authenticate {
            route("/api/user") {
                get("/info") {
                    application.log.info("userId: ${MDC.get("userId")}")
                }
            }
        }
    }
}
