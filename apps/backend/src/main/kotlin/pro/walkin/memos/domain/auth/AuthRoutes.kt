package pro.walkin.memos.domain.auth

import domain.auth.UserSession
import domain.user.HashedPassword
import domain.user.UserName
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
import pro.walkin.memos.domain.user.UserQuery
import pro.walkin.memos.error.ArgumentVerificationException
import pro.walkin.memos.error.userMessages

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
                    val signedUser =
                        authService.signin(
                            UserName.from(signIn.username),
                            HashedPassword.from(signIn.password)
                        )
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
