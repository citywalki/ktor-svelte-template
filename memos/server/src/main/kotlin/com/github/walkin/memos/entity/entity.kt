package com.github.walkin.memos.entity

import kotlinx.datetime.LocalDateTime
import org.komapper.annotation.*

open class BaseEntity(
  @KomapperVersion val version: Int = 0,
  @KomapperCreatedAt val createdAt: LocalDateTime? = null,
  @KomapperUpdatedAt val updatedAt: LocalDateTime? = null,
)

open class IDBaseEntity(@KomapperId @KomapperAutoIncrement val id: EntityID? = null) : BaseEntity()
