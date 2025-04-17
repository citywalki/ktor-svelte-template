package pro.walkin.memo.config

import domain.UserId
import domain.UserName
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.logging.*
import kotlinx.coroutines.asContextElement
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.jsonPrimitive
import org.slf4j.kotlin.getLogger
import pro.walkin.memo.route.auth
import query.Query
import service.Service
import utils.SecurityContextHolder
import utils.SecurityInfo
import utils.securityInfoThreadLocal

val SecurityContextPlugin = createApplicationPlugin(name = "CallContextPlugin") {

    application.intercept(ApplicationCallPipeline.ApplicationPhase.Call) {
      withContext(securityInfoThreadLocal.asContextElement(SecurityInfo())) {
        proceed()
      }
    }
  }

val FillSecurityContextContextEnricher = createRouteScopedPlugin(name = "FillSecurityContext") {
  on(AuthenticationChecked) { call ->
    val securityInfo = call.principal<SecurityInfo>() ?: return@on

    SecurityContextHolder.set(securityInfo)
  }
}

fun Application.configureRouting(query: Query, service: Service) {
  install(SecurityContextPlugin)

  val logger by getLogger("web")

  install(ContentNegotiation) { json() }

  install(StatusPages) {
    exception<Throwable> { call, cause ->
      logger.error(cause)
      call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
    }
  }

  routing {
    install(FillSecurityContextContextEnricher)
    swaggerUI(path = "openapi")
    openAPI(path = "openapi")

    route("/api/v1") { auth(query, service) }
  }
}
