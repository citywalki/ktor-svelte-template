package com.github.walkin.memos.config

import kotlinx.serialization.json.Json
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder

@Configuration
class MemosConfig {
  @Bean
  fun jsonDecoder(): KotlinSerializationJsonDecoder =
    KotlinSerializationJsonDecoder(Json { ignoreUnknownKeys = true })
}
