package pro.walkin.memo.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import domain.UserId
import domain.UserName
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.response.*
import utils.SecurityInfo

fun ApplicationConfig.getJwtIssuer() = propertyOrNull("jwt.issuer")?.getString() ?: "jwt"
fun ApplicationConfig.getJwtRealm() = propertyOrNull("jwt.realm")?.getString() ?: "jwt"
fun ApplicationConfig.getJwtAudience() = propertyOrNull("jwt.audience")?.getString() ?: "jwt"
fun ApplicationConfig.getJwtSecret() = propertyOrNull("jwt.secret")?.getString()?.toByteArray() ?: ByteArray(0)

fun Application.configureSecurity() {

    val issuer = environment.config.getJwtIssuer()
    val myRealm = environment.config.getJwtRealm()
    val audience = environment.config.getJwtAudience()
    val secret = environment.config.getJwtSecret()

    authentication {
        // jwt-kt
//        jwt {
//            realm = myRealm
//            verifier(issuer, audience) {
//                hs256 { this.secret = secret }
//            }
//
//            validate { credential ->
//                if (credential.claims["username"]?.jsonPrimitive?.content != "") {
//                    JWTPrincipal(credential.claims)
////                    SecurityInfo(
////                        username = UserName(credential.claims["username"]!!.jsonPrimitive.content),
////                    )
//                } else {
//                    null
//                }
//            }
//
//            challenge { defaultScheme, realm ->
//                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
//            }
//        }
        jwt {
            realm = myRealm
            verifier(
                JWT
                .require(Algorithm.HMAC256(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build())

            validate { credential ->
                if (credential.payload.getClaim("username").asString().isNotEmpty() && credential.subject?.isNotEmpty() == true) {
                    SecurityInfo(
                        username = UserName.from(credential.payload.getClaim("username").asString()),
                        userId = UserId(credential.subject!!.toLong())
                    )
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}
