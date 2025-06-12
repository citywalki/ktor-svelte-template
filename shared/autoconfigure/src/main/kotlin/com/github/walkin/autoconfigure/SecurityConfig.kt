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
        "/api/login",
        "/api/auth/signin",
        "/api/auth/refresh_token",
        "/api/auth/signup",
        "/webjars/**",
        "/swagger-ui.html",
        "/v3/api-docs/swagger-config",
        "/v3/api-docs",
        "/swagger-ui/**",
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
