package app.backend.domain.auth

import model.auth.UserSession
import model.user.HashedPassword
import model.user.UserName
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import kotlinx.serialization.Serializable
import org.komapper.r2dbc.R2dbcDatabase
import pro.walkin.logging.I18nMessages
import app.backend.domain.user.UserQuery
import app.backend.error.ArgumentVerificationException
import app.backend.error.userMessages

@Serializable
data class SignIn(val username: String, val password: String, val neverExpire: Boolean? = false)

@Serializable
data class SignInForEmail(val email: String, val password: String, val neverExpire: Boolean? = false)

@Serializable
data class SignUp(val username: String, val password: String)

suspend fun Application.authRoutes(
    authService: AuthService,
    database: R2dbcDatabase,
    userQuery: UserQuery
) {
    routing {
        route("/api/auth") {
            post("/signin") {
                database.withTransaction {
                    val signIn = call.receive<SignIn>()

                    val username = UserName.from(signIn.username)
                    val password = HashedPassword.from(signIn.password)

                    if (userQuery.findUser(username) == null) {
                        throw ArgumentVerificationException("username", I18nMessages.userMessages.userNotExist())
                    }

                    val signedUser = authService.signin(username, password)
                    call.sessions.set(UserSession.create(signedUser.id))
                    call.respond(HttpStatusCode.OK)
                }
            }
            post("/signup") {
                database.withTransaction { _ ->
                    val signUp = call.receive<SignUp>()

                    val username = UserName.from(signUp.username)
                    val password = HashedPassword.from(signUp.password)

                    if (userQuery.findUser(username) != null) {
                        throw ArgumentVerificationException("username", I18nMessages.userMessages.userSameExist())
                    }

                    val signedUser = authService.signup(username, password)

                    call.sessions.set(UserSession.create(signedUser.id))
                    call.respond(HttpStatusCode.OK)
                }
            }
            authenticate {
                post("/signOut") {
                }
            }
        }
    }
}
