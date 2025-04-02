package com.github.walkin.memos.domain

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
