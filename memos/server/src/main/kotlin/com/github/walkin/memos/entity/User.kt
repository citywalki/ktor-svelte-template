package com.github.walkin.memos.entity

import com.github.walkin.memos.domain.User
import com.github.walkin.memos.domain.UserId
import com.github.walkin.memos.entity.InboxTable.entityId
import com.github.walkin.shared.entity.RowStatus
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.CompositeIdTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column

@Serializable
enum class UserRole {
  ROLE_UNSPECIFIED,
  HOST,
  ADMIN,
  USER,
}

object UserTable : BaseIdTable<UserId>("memos_users") {

  val rowStatus = enumeration("row_status", RowStatus::class).default(RowStatus.NORMAL)
  val role = enumeration("role", UserRole::class)
  val email = varchar("email", 255).nullable()
  val username = varchar("username", 255).nullable()
  val password = varchar("password", 255)
  var avatarUrl = varchar("avatar_url", 255).nullable()
  override val id: Column<EntityID<UserId>> =
    long("id")
      .transform({ UserId(it) }, { it.value })
      .clientDefault { UserId(Clock.System.now().epochSeconds) }
      .entityId()

  override val primaryKey: PrimaryKey = PrimaryKey(id)
}

class UserEntity(id: EntityID<UserId>) : BaseEntity<UserId>(id, UserTable) {
  companion object : BaseEntityClass<UserId, UserEntity>(UserTable)

  var rowStatus by UserTable.rowStatus
  var role by UserTable.role
  var email by UserTable.email
  var username by UserTable.username
  var password by UserTable.password
  var avatarUrl by UserTable.avatarUrl

  fun toModel(): User {
    return User(id = id.value)
  }
}

object UserSpaceTable : LongIdTable("user_space") {
  val name = varchar("name", 255)
  val user = reference("user", UserTable)
}

class UserSpaceEntity(id: EntityID<Long>) : LongIdEntity(id, UserSpaceTable) {
  companion object : LongIdEntityClass<UserSpaceEntity>(UserSpaceTable)

  var name by UserSpaceTable.name
  var user by UserEntity referencedOn UserSpaceTable.user
}

object UserSettingTable : CompositeIdTable("user_setting") {
  val key = enumerationByName("key", 25, UserSettingKey::class).entityId()
  val user = reference("user_id", UserTable).entityId()
  val value = text("value")

  override val primaryKey = PrimaryKey(key, user)
}

class UserSettingEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {
  companion object : CompositeEntityClass<UserSettingEntity>(UserSettingTable)

  val userId by UserSettingTable.user.entityId()
  val key by UserSettingTable.key
  val value by UserSettingTable.value
}

@Serializable
data class UserStats(
  val name: String,
  val memoDisplayTimestamps: List<LocalDateTime>,
  val memoTypeStats: MemoTypeStats,
  val tagCount: Map<String, Int>,
)

@Serializable
data class MemoTypeStats(
  val linkCount: Int,
  val codeCount: Int,
  val todoCount: Int,
  val undoCount: Int,
)

enum class UserSettingKey {
  USER_SETTING_KEY_UNSPECIFIED,
  // Access tokens for the user.
  ACCESS_TOKENS,
  // The locale of the user.
  LOCALE,
  // The appearance of the user.
  APPEARANCE,
  // The visibility of the memo.
  MEMO_VISIBILITY,
  // The shortcuts of the user.
  SHORTCUTS,
}
