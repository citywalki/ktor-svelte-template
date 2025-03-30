package com.github.walkin.memos.entity

import com.github.walkin.memos.domain.MemosVisibility
import com.github.walkin.shared.entity.RowStatus
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.komapper.annotation.*
import org.komapper.core.type.ClobString

@KomapperEntity
@KomapperTable(name = "memo")
@Serializable
data class Memo(
  @KomapperId @KomapperAutoIncrement val uid: EntityID = 0,
  var rowStatus: RowStatus? = RowStatus.NORMAL,
  var creator: EntityID,
  @KomapperColumn(alternateType = ClobString::class) var content: String,
  var payload: MemoPayload = MemoPayload(),
  var visibility: MemosVisibility = MemosVisibility.PRIVATE,
  @KomapperCreatedAt val createdAt: LocalDateTime? = null,
  @KomapperUpdatedAt val updatedAt: LocalDateTime? = null,
)

@Serializable
data class MemoPayload(
  val tags: Set<String> = mutableSetOf(),
  val property: Property = Property(),
  val location: Location = Location(),
) {
  @Serializable
  data class Property(
    var hasLink: Boolean = false,
    var hasTaskList: Boolean = false,
    var hasCode: Boolean = false,
    var hasIncompleteTasks: Boolean = false,
    var references: List<String> = mutableListOf(),
  )

  @Serializable
  data class Location(
    val placeholder: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
  )
}
