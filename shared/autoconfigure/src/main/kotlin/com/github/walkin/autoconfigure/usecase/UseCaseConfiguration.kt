package com.github.walkin.autoconfigure.usecase

import com.github.walkin.usecase.CommandPublish
import com.github.walkin.usecase.CommandPublishImpl
import com.github.walkin.usecase.UseCaseBeanPost
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean

@AutoConfiguration
@ConditionalOnClass(CommandPublishImpl::class)
open class UseCaseConfiguration {

  @Bean
  fun commandPublish(): CommandPublish {
    return CommandPublishImpl()
  }

  @Bean
  fun usecaseBeanPose(commandPublish: CommandPublish): UseCaseBeanPost {
    return UseCaseBeanPost(commandPublish)
  }
}
