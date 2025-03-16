package com.github.walkin.usecase.autoconfigure

import com.github.walkin.usecase.CommandPublish
import com.github.walkin.usecase.CommandPublishImpl
import com.github.walkin.usecase.UseCaseBeanPost
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.Bean

@AutoConfiguration
open class UseCaseConfiguration {

  @Bean
  open fun commandPublish(): CommandPublish {
    return CommandPublishImpl()
  }

  @Bean
  open fun usecaseBeanPose(commandPublish: CommandPublish): UseCaseBeanPost {
    return UseCaseBeanPost(commandPublish)
  }
}
