package pro.walkin.memos.auth

import domain.GeneralSystemSettingDetail
import domain.user.HashedPassword
import domain.user.NickName
import domain.user.User
import domain.user.UserId
import domain.user.UserName
import domain.user.UserRole
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.komapper.r2dbc.R2dbcDatabase
import org.komapper.tx.core.CoroutineTransactionOperator
import org.komapper.tx.core.TransactionAttribute
import org.komapper.tx.core.TransactionProperty
import pro.walkin.memos.configure.configureWeb
import pro.walkin.memos.domain.auth.AuthService
import pro.walkin.memos.domain.auth.SignIn
import pro.walkin.memos.domain.auth.SignUp
import pro.walkin.memos.domain.auth.authRoutes
import pro.walkin.memos.domain.system.persistence.SystemSettingDAOFacade
import pro.walkin.memos.domain.user.UserDAO
import pro.walkin.memos.domain.user.create
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthRoutesTest {

    private val db = object : R2dbcDatabase by mockk(relaxed = true) {
        private val operator = mockk<CoroutineTransactionOperator>(relaxed = true)
        override suspend fun <R> withTransaction(
            transactionAttribute: TransactionAttribute,
            transactionProperty: TransactionProperty,
            block: suspend (CoroutineTransactionOperator) -> R,
        ): R {
            return block(operator)
        }
    }

    private val systemSettingDAOFacade = mockk<SystemSettingDAOFacade>(relaxed = true)

    private val userDAO = mockk<UserDAO>(relaxed = true)

    fun ApplicationTestBuilder.client() = createClient {
        install(ContentNegotiation) {
            json()
        }
    }

    @Test
    fun testSignin() = testApplication {
        setupApp()

        val client = client()
        client.post("/api/auth/signin") {
            contentType(ContentType.Application.Json)
            setBody(
                SignIn(
                    username = "username",
                    password = "password",
                )
            )
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testSignup() = testApplication {
        setupApp()

        coEvery { systemSettingDAOFacade.findGeneralSystemSetting() } returns GeneralSystemSettingDetail()
        coEvery { userDAO.countUser(UserRole.HOST) } returns 0

        val client = client()
        client.post("/api/auth/signup") {
            contentType(ContentType.Application.Json)
            setBody(
                SignUp(
                    username = "username",
                    password = "password",
                )
            )
        }.apply {
            coVerify {
                userDAO.insertUser(
                    User(
                        id = UserId.create(),
                        username = UserName.from("username"),
                        hashedPassword = HashedPassword.from("password"),
                        role = UserRole.HOST,
                        nickname = NickName("username"),
                    )
                )
            }
            assertEquals(HttpStatusCode.OK, status)
        }

//        confirmVerified(userDAOFacade, systemSettingDAOFacade)
    }

    private fun ApplicationTestBuilder.setupApp() {
        application {
            configureWeb()
            dependencies {
                provide { db }
            }
            authRoutes(
                authService = AuthService(db, userDAO, systemSettingDAOFacade),
                database = db
            )
        }
        environment {
            config = ApplicationConfig("application-test.conf")
        }
    }
}
