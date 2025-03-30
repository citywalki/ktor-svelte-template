package com.github.walkin.memos

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class MemosApplication {
  @Bean
  fun initRun(datasource: R2dbcDatabase): CommandLineRunner {
    return CommandLineRunner {
      CoroutineScope(Dispatchers.IO).launch {
        datasource.runQuery {
          QueryDsl.create(
            Entity.user,
            Entity.workspaceSetting,
            Entity.inbox,
            Entity.userSetting,
            Entity.userSpace,
          )
        }
      }
    }
  }
}

fun main(args: Array<String>) {
  runApplication<MemosApplication>(*args)
}
