package pro.walkin.memos

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import pro.walkin.memos.auth.authRoutes

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

suspend fun Application.module() {
    install(CallId) {
        header(HttpHeaders.XRequestId)
        verify { callId: String ->
            callId.isNotEmpty()
        }
    }
    install(CallLogging) {
        callIdMdc("call-id")
    }
    install(ContentNegotiation) {
        json()
    }

    authentication {
        val realm = this@module.property<String>(PropertyKeys.Jwt.REALM)
        val secret = this@module.property<String>(PropertyKeys.Jwt.SECRET)
        val jwtAudience = this@module.property<String>(PropertyKeys.Jwt.AUDIENCE)
        val jwtDomain = this@module.property<String>(PropertyKeys.Jwt.DOMAIN)

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

    dependencies()
    routing {
        swaggerUI(path = "openapi")
        openAPI(path = "openapi")
    }
    authRoutes()
}
