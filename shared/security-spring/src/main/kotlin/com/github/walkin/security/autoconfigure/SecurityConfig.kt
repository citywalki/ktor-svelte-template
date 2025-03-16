package com.github.walkin.security.autoconfigure

import cn.dev33.satoken.reactor.filter.SaReactorFilter
import cn.dev33.satoken.stp.StpUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SecurityConfig {
  companion object {
    val EXCLUDED_PATHS =
      arrayOf(
        "/login",
        "/",
        "/static/**",
        "/index.html",
        "/favicon.ico",
        "/docs/**",
        "/docs.yaml",
        "/webjars/**",
        "/swagger-ui.html",
      )
  }

  @Bean
  fun getSaReactorFilter(): SaReactorFilter {
    return SaReactorFilter().addExclude(*EXCLUDED_PATHS).setAuth { if (!StpUtil.isLogin()) {} }
  }
}
