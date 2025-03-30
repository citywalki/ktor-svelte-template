package com.github.walkin.autoconfigure

import com.github.walkin.security.PasswordEncoder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SecurityConfig {
  companion object {
    val EXCLUDED_PATHS =
      arrayOf(
        "/graphql",
        "/api/*/login",
        "/webjars/**",
        "/swagger-ui.html",
        "/v3/api-docs/swagger-config",
        "/v3/api-docs",
        "/api/*/auth/signup",
        "/api/*/auth/signip",
        "/api/*/workspace/profile",
        "/api/*/workspace/GENERAL",
        "/api/*/workspace/MEMO_RELATED",
        "/api/*/auth/refresh_token",
      )
  }

  @Bean
  @ConditionalOnMissingBean
  fun defaultPasswordEncoder(): PasswordEncoder {
    return object : PasswordEncoder {
      override fun encode(password: String): String {
        return password
      }
    }
  }
}
