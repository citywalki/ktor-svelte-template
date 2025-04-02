package com.github.walkin.autoconfigure

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@ConditionalOnClass
class WebFluxConfigurer(private val jsonDecoder: KotlinSerializationJsonDecoder) :
  WebFluxConfigurer {

  override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
    configurer.defaultCodecs().kotlinSerializationJsonDecoder(jsonDecoder)
  }
}
