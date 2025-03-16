package com.github.walkin.usecase

abstract class UseCase<C : Command<R>, R>() {

  abstract suspend fun handle(command: C): R

  fun condition(command: C): Boolean = true

  fun priority(): Int = 0

  fun getUsecaseType() = this.javaClass
}
