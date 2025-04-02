package com.github.walkin.autoconfigure

import kotlinx.serialization.json.Json
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder

@Configuration
class WebSerializationConfig {
  companion object {
    val json = Json { ignoreUnknownKeys = true }
  }

  @Bean fun jsonDecoder(): KotlinSerializationJsonDecoder = KotlinSerializationJsonDecoder(json)

  @Bean fun jsonEncoder(): KotlinSerializationJsonEncoder = KotlinSerializationJsonEncoder(json)
}
