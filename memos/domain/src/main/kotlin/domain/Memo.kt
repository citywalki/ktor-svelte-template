package domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
enum class MemosVisibility {
    VISIBILITY_UNSPECIFIED,
    PRIVATE,
    PROTECTED,
    PUBLIC,
}

@Serializable
data class MemoResource(
    val id: Long,
    val createTime: LocalDateTime,
    var filename: String,
    var content: Array<Byte>,
    var externalLink: String,
    var type: String,
    var size: Long,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MemoResource) return false

        if (id != other.id) return false
        if (size != other.size) return false
        if (createTime != other.createTime) return false
        if (filename != other.filename) return false
        if (!content.contentEquals(other.content)) return false
        if (externalLink != other.externalLink) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + size.hashCode()
        result = 31 * result + createTime.hashCode()
        result = 31 * result + filename.hashCode()
        result = 31 * result + content.contentHashCode()
        result = 31 * result + externalLink.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}

@Serializable
enum class MemoRelationType {

    TYPE_UNSPECIFIED,
    REFERENCE,
    COMMENT,
}
