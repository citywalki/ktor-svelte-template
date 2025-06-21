package domain

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Serializable
enum class RowStatus {
    NORMAL,
    ARCHIVED,
}

typealias TableId = Long

typealias PageToken = String

@OptIn(ExperimentalEncodingApi::class)
fun PageToken.toPayload(): PageTokenPayload {
    val b = Base64.decode(this)

    return Json.decodeFromString<PageTokenPayload>(String(b))
}

@Serializable
data class PageTokenPayload(val limit: Int, val offset: Int) {
    @OptIn(ExperimentalEncodingApi::class)
    fun encode(): PageToken {
        val json = Json.encodeToString(this)
        return Base64.encode(json.toByteArray())
    }
}

@Serializable
data class ArgumentErrorMessage(
    private val field: String,
    private val message: String?,
)

@Serializable
data class ErrorResponse(val code: Int, val message: String? = null, val fields: List<ArgumentErrorMessage>? = null) {
    object ErrorResponseCode {
        const val FIELD_ERROR = 1000
    }
}
