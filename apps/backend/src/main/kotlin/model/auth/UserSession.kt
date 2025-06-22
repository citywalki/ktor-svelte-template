package model.auth

import model.user.UserId
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.minutes

@Serializable
data class UserSession(val userId: UserId, val expirationTime: Instant) {
    companion object {
        fun create(userId: UserId): UserSession {

            return UserSession(userId, Clock.System.now().plus(5.minutes))
        }
    }
}
