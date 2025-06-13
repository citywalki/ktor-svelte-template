package memos.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import domain.HashedPassword
import domain.UserName
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import kotlinx.serialization.Serializable
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@Serializable
data class SignIn(val username: String, val password: String, val neverExpire: Boolean? = false)

@Serializable
data class SignUp(val username: String, val password: String)

interface AuthController {

    @PostMapping("/auth/signin")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "List subscriptions"),
            ApiResponse(responseCode = "400", description = "Slack user not found")
        ]
    )
    fun signin(
        @RequestBody signIn: SignIn
    ): ResponseEntity<Map<String, String>>

    @PostMapping("/auth/signup")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "List subscriptions"),
            ApiResponse(responseCode = "400", description = "Slack user not found")
        ]
    )
    fun signup(
        @RequestBody signUp: SignUp
    ): ResponseEntity<Map<String, String>>
}

@RestController
class DefaultAuthController(
    val authService: AuthService,
    @param:Value("\${jwt.secret}") val jwtSecret: String,
    @param:Value("\${jwt.issuer}") val jwtIssuer: String
) : AuthController {
    override fun signin(signIn: SignIn): ResponseEntity<Map<String, String>> {
        val signedUser =
            authService.signin(UserName.from(signIn.username), HashedPassword.from(signIn.password), signIn.neverExpire)

        var expireTime = Clock.System.now().plus(10.minutes).toJavaInstant()
        if (signIn.neverExpire == true) {
            @Suppress("MagicNumber")
            expireTime = Clock.System.now().plus((100 * 365 * 24).hours).toJavaInstant()
        }

        val token = JWT.create()
            .withSubject(signedUser.id.value.toString())
            .withIssuer(jwtIssuer)
            .withClaim("username", signedUser.username.value)
            .withExpiresAt(Date.from(expireTime))
            .sign(Algorithm.HMAC256(jwtSecret))

        return ResponseEntity.ok(hashMapOf("token" to token.toString()))
    }

    override fun signup(signUp: SignUp): ResponseEntity<Map<String, String>> {
        TODO("Not yet implemented")
    }
}
