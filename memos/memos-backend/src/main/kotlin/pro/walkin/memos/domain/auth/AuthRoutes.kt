package pro.walkin.memos.domain.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import domain.Email
import domain.HashedPassword
import domain.UserName
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.config.property
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import kotlinx.serialization.Serializable
import org.komapper.r2dbc.R2dbcDatabase
import pro.walkin.memos.PropertyKeys
import java.util.Date
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@Serializable
data class SignIn(val username: String, val password: String, val neverExpire: Boolean? = false)

@Serializable
data class SignInForEmail(val email: String, val password: String, val neverExpire: Boolean? = false)

@Serializable
data class SignUp(val username: String, val password: String)

fun Application.authRoutes(
    authService: AuthService,
    database: R2dbcDatabase
) {
    routing {
        route("/api/auth") {
            post("/signin") {
                val signIn = call.receive<SignIn>()
                val token = database.withTransaction {
                    val signedUser =
                        authService.signin(
                            UserName.from(signIn.username),
                            HashedPassword.from(signIn.password)
                        )

                    var expireTime = Clock.System.now().plus(10.minutes).toJavaInstant()
                    if (signIn.neverExpire == true) {
                        @Suppress("MagicNumber")
                        expireTime = Clock.System.now().plus((100 * 365 * 24).hours).toJavaInstant()
                    }

                    val jwtSecret = this@authRoutes.property<String>(PropertyKeys.Jwt.SECRET)
                    val jwtDomain = this@authRoutes.property<String>(PropertyKeys.Jwt.DOMAIN)

                    JWT.create()
                        .withSubject(signedUser.id.value.toString())
                        .withIssuer(jwtDomain)
                        .withClaim("username", signedUser.username.value)
                        .withExpiresAt(Date.from(expireTime))
                        .sign(Algorithm.HMAC256(jwtSecret))
                }

                call.respond(mapOf("token" to token))
            }
            post("/signin/email") {
                val signIn = call.receive<SignInForEmail>()

                val signedUser =
                    authService.signinForEmail(
                        Email.from(signIn.email),
                        HashedPassword.from(signIn.password)
                    )

                var expireTime = Clock.System.now().plus(10.minutes).toJavaInstant()
                if (signIn.neverExpire == true) {
                    @Suppress("MagicNumber")
                    expireTime = Clock.System.now().plus((100 * 365 * 24).hours).toJavaInstant()
                }

                val jwtSecret = this@authRoutes.property<String>(PropertyKeys.Jwt.SECRET)
                val jwtDomain = this@authRoutes.property<String>(PropertyKeys.Jwt.DOMAIN)

                val token = JWT.create()
                    .withSubject(signedUser.id.value.toString())
                    .withIssuer(jwtDomain)
                    .withClaim("username", signedUser.username.value)
                    .withExpiresAt(Date.from(expireTime))
                    .sign(Algorithm.HMAC256(jwtSecret))

                call.respond(mapOf("token" to token))
            }
            post("/signup") {
                val signUp = call.receive<SignUp>()

                authService.signup(
                    userName = UserName.from(signUp.username),
                    password = HashedPassword.from(signUp.password)
                )

                call.response.status(HttpStatusCode.OK)
            }
            authenticate {
                post("/signOut") {
                }
            }
        }
    }
}
