package pro.walkin.memos.configure

import domain.ArgumentErrorMessage
import domain.ErrorResponse
import domain.ErrorResponse.ErrorResponseCode.FIELD_ERROR
import domain.auth.UserSession
import domain.user.UserId
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.auth.authentication
import io.ktor.server.auth.principal
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
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing
import io.ktor.server.sessions.SessionTransportTransformerEncrypt
import io.ktor.server.sessions.SessionTransportTransformerMessageAuthentication
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.util.hex
import kotlinx.datetime.Clock
import pro.walkin.memos.error.ArgumentVerificationException

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
        val secret = this@configureWeb.property<String>("session.secret")
        val secretSignKey = hex(secret)
        val secretEncryptKey = "6164666164666164666461".encodeToByteArray().copyOf(16)
        val secretAuthKey = "123dsvadfcasdf".encodeToByteArray()
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
                call.respondText(text = cause.message ?: "system error", status = HttpStatusCode.InternalServerError)
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
