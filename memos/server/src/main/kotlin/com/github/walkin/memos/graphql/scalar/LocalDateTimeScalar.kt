package com.github.walkin.memos.graphql.scalar

import graphql.GraphQLContext
import graphql.scalars.util.Kit
import graphql.schema.Coercing
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import java.util.*
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char

object LocalDateTimeScalar {
  val dateFormat =
    LocalDateTime.Format {
      year()
      char('-')
      monthNumber()
      char('-')
      dayOfMonth()
    }

  private val coercing =
    object : Coercing<LocalDateTime, String> {

      override fun serialize(input: Any, graphQLContext: GraphQLContext, locale: Locale): String? {
        val localDateTime: LocalDateTime
        when (input) {
          is LocalDateTime -> {
            localDateTime = input
            return localDateTime.format(dateFormat)
          }

          is String -> {
            return input
          }

          else -> {
            throw CoercingSerializeException(
              "Expected something we can convert to 'kotlinx.datetime.LocalDateTime' but was '" +
                Kit.typeName(input) +
                "'."
            )
          }
        }
      }

      override fun parseValue(
        input: Any,
        graphQLContext: GraphQLContext,
        locale: Locale,
      ): LocalDateTime? {
        when (input) {
          is String -> {
            LocalDateTime.parse(input, dateFormat)
          }

          is LocalDateTime -> {
            return input
          }

          else -> {
            throw CoercingParseValueException(
              "Expected a 'String' but was '" + Kit.typeName(input) + "'."
            )
          }
        }
        return super.parseValue(input, graphQLContext, locale)
      }
    }

  val Instance: GraphQLScalarType =
    GraphQLScalarType.newScalar()
      .name("LocalDateTime")
      .description("KOTLIN DATETIME")
      .coercing(coercing)
      .build()
}
