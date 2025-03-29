package com.github.walkin.memos.config

import com.github.walkin.memos.graphql.scalar.LocalDateTimeScalar
import graphql.scalars.ExtendedScalars
import graphql.schema.idl.RuntimeWiring
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer

@Configuration
class GraphqlConfig {

  @Bean
  fun runtimeWiringConfigurer(): RuntimeWiringConfigurer {
    return RuntimeWiringConfigurer { wiringBuilder: RuntimeWiring.Builder ->
      wiringBuilder.scalar(ExtendedScalars.Date)
      wiringBuilder.scalar(ExtendedScalars.GraphQLLong)
      wiringBuilder.scalar(ExtendedScalars.DateTime)
      wiringBuilder.scalar(ExtendedScalars.Time)
      wiringBuilder.scalar(LocalDateTimeScalar.Instance)
    }
  }
}
