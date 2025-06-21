package pro.walkin.memos.domain.user

import domain.auth.UserSession
import domain.user.UserId
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.RoutingContext
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.komapper.r2dbc.R2dbcDatabase
import org.slf4j.MDC

suspend fun Application.userRoutes(
    userQuery: UserQuery,
) {
    routing {
        authenticate {
            route("/api/user") {
                get("/me") {
                    withBizContext {
                        val userId = UserId.from(MDC.get("userId"))

                        val user = userQuery.findUser(userId)
                        if (user == null) {
                            call.respondRedirect("/login")
                        } else {
                            call.respond(HttpStatusCode.OK, user)
                        }
                    }
                }
            }
        }
    }
}

suspend fun <R> RoutingContext.withBizContext(
    block: suspend () -> R,
): R{
    val userSession = call.principal<UserSession>()
    val database = call.application.dependencies.resolve<R2dbcDatabase>()

   return if (userSession != null) {
        MDC.put("userId", userSession.userId.value.toString())
        withContext(MDCContext()){
            database.withTransaction {
                block()
            }
        }
    }else{
        database.withTransaction {
            block()
        }
    }

}
