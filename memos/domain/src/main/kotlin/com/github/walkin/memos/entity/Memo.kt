package com.github.walkin.memos.entity

import com.github.walkin.shared.entity.RowStatus
import kotlinx.datetime.LocalDateTime

data class Memo(
  val uid: String,
  var rowStatus: RowStatus? = RowStatus.NORMAL,
  var creator: Long,
  var content: String,
  var payload: MemoPayload = MemoPayload(),
  var visibility: MemosVisibility = MemosVisibility.PRIVATE,
  val createdAt: LocalDateTime? = null,
)

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

enum class MemosVisibility {
  VISIBILITY_UNSPECIFIED,
  PRIVATE,
  PROTECTED,
  PUBLIC,
}

enum class MemoRelationType {

  TYPE_UNSPECIFIED,
  REFERENCE,
  COMMENT,
}

data class MemoPayload(
  val tags: Set<String> = mutableSetOf(),
  val property: Property = Property(),
  val location: Location = Location(),
) {
  data class Property(
    var hasLink: Boolean = false,
    var hasTaskList: Boolean = false,
    var hasCode: Boolean = false,
    var hasIncompleteTasks: Boolean = false,
    var references: List<String> = mutableListOf(),
  )

  data class Location(
    val placeholder: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
  )
}
