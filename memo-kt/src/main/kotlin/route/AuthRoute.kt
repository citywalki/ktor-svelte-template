package pro.walkin.memo.route

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import domain.HashedPassword
import domain.User
import domain.UserName
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import kotlinx.serialization.Serializable
import pro.walkin.memo.config.getJwtAudience
import pro.walkin.memo.config.getJwtIssuer
import pro.walkin.memo.config.getJwtSecret
import query.Query
import query.getAuthStatus
import service.Service
import service.signin
import service.signup
import java.util.Date
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@Serializable
data class SignIn(val username: String, val password: String, val neverExpire: Boolean? = false)

@Serializable data class SignUp(val username: String, val password: String)

fun Route.auth(query: Query, service: Service) {
  route("/auth") {
    post("/signin") {
      val body = call.receive<SignIn>()

      val signedUser = service.signin(UserName.from(body.username), HashedPassword.from(body.password))

      var expireTime = Clock.System.now().plus(10.minutes).toJavaInstant()
      if (body.neverExpire == true) {
        expireTime = Clock.System.now().plus((100 * 365 * 24).hours).toJavaInstant()
      }

      val issuer = environment.config.getJwtIssuer()
      val audience = environment.config.getJwtAudience()
      val secret = environment.config.getJwtSecret()

      val token = JWT.create()
        .withAudience(audience)
        .withSubject(signedUser.id.value.toString())
        .withIssuer(issuer)
        .withClaim("username", signedUser.username.value)
        .withExpiresAt(Date.from(expireTime))
        .sign(Algorithm.HMAC256(secret))
//        jwt-kt
//        jwt {
//        claims {
//          this.audience = myAudience
//          this.issuer = myIssuer
//          this.subject = signedUser.id.value.toString()
//          claim("username", signedUser.username)
//          expires(expireTime)
//        }
//      }
//        .sign { hs256 { this.secret = secret } }

      call.respond(hashMapOf("token" to token.toString()))
    }

    post("/signup") {
      val body = call.receive<SignUp>()
      service.signup(UserName.from(body.username), HashedPassword.from(body.password))

      call.respond(HttpStatusCode.OK)
    }

    authenticate {
      get("/status") {
        val user: User? = query.getAuthStatus()

        call.respond(user ?: HttpStatusCode.OK)
      }
    }
  }
}
