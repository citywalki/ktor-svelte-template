package app.backend.configure

import model.ArgumentErrorMessage
import model.ErrorResponse
import model.ErrorResponse.ErrorResponseCode.FIELD_ERROR
import model.auth.UserSession
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.auth.authentication
import io.ktor.server.auth.session
import io.ktor.server.config.property
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import io.ktor.server.sessions.SessionTransportTransformerEncrypt
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.util.hex
import kotlinx.datetime.Clock
import app.backend.error.ArgumentVerificationException

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
    }

    install(Sessions) {
        val encryptKey = this@configureWeb.property<String>("session.secret.encrypt")
        val authKey = this@configureWeb.property<String>("session.secret.encrypt")

        val secretEncryptKey = encryptKey.encodeToByteArray().copyOf(16)
        val secretAuthKey = authKey.encodeToByteArray()
        cookie<UserSession>("user_session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 7L * 24 * 3600 // 7 days
            transform(SessionTransportTransformerEncrypt(secretEncryptKey,secretAuthKey))
        }
    }

    authentication {
        session<UserSession> {
            validate { session ->
                val expirationTime = session.expirationTime
                if (expirationTime > Clock.System.now()) {
                    session
                } else {
                    null
                }
            }
            challenge {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.application.log.error(cause.message, cause)
            if (cause is ArgumentVerificationException) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ErrorResponse(FIELD_ERROR, fields = listOf(ArgumentErrorMessage(cause.field, cause.message)))
                )
            } else {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse(1, cause.message ?: "system error"))
            }
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
