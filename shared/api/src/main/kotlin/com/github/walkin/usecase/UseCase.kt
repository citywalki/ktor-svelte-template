package com.github.walkin.usecase

import kotlin.reflect.KClass

abstract class UseCase<C : Command<R>, R>() {

  abstract fun handle(command: C): R

  fun condition(command: C): Boolean = true

  fun priority(): Int = 0

  fun getUsecaseType() = this.javaClass

  abstract fun getCommandType(): KClass<C>
}
