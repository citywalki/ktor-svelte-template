package com.github.walkin.shared.entity

import kotlinx.serialization.Serializable

@Serializable
enum class RowStatus {
  NORMAL,
  ARCHIVED,
}
