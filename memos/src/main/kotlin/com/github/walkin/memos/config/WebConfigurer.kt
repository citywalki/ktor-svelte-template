package com.github.walkin.memos.config

import com.github.walkin.memos.MemosController
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.PathMatchConfigurer
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
class WebConfigurer(private val jsonDecoder: KotlinSerializationJsonDecoder) : WebFluxConfigurer {

  override fun configurePathMatching(configurer: PathMatchConfigurer) {
    configurer.addPathPrefix("/api/v1") { it.isAnnotationPresent(MemosController::class.java) }
  }

  override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
    configurer.defaultCodecs().kotlinSerializationJsonDecoder(jsonDecoder)
  }
}
