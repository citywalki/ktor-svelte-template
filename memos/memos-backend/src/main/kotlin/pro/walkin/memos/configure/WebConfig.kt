package pro.walkin.memos.configure

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.auth.principal
import io.ktor.server.config.property
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing
import pro.walkin.memos.PropertyKeys

fun Application.configureWeb() {
    install(ContentNegotiation) {
        json()
    }
    install(CallId) {
        header(HttpHeaders.XRequestId)
        verify { callId: String ->
            callId.isNotEmpty()
        }
    }
    install(CallLogging) {
        callIdMdc("call-id")
        mdc("userId") { call ->
            val principal = call.principal<JWTPrincipal>()

            principal?.get("userId") ?: ""
        }
    }

    authentication {
        val realm = this@configureWeb.property<String>(PropertyKeys.Jwt.REALM)
        val secret = this@configureWeb.property<String>(PropertyKeys.Jwt.SECRET)
        val jwtAudience = this@configureWeb.property<String>(PropertyKeys.Jwt.AUDIENCE)
        val jwtDomain = this@configureWeb.property<String>(PropertyKeys.Jwt.DOMAIN)

        jwt {
            this.realm = realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(secret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.application.log.error(cause.message, cause)
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
}

fun Application.configureDevWeb() {
    if (developmentMode) {
        routing {
            swaggerUI(path = "openapi")
            openAPI(path = "openapi")
        }
    }
}
