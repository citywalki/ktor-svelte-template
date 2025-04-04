package com.github.walkin.memos

import com.github.walkin.memos.entity.SystemSettingTable
import com.github.walkin.memos.entity.UserSpaceTable
import com.github.walkin.memos.entity.UserTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class MemosApplication {
  @Bean
  fun initRun(): CommandLineRunner {
    return CommandLineRunner {
      transaction { SchemaUtils.create(UserTable, SystemSettingTable, UserSpaceTable) }
    }
  }
}

fun main(args: Array<String>) {
  runApplication<MemosApplication>(*args)
}
