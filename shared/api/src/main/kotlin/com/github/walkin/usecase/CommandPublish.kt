package com.github.walkin.usecase

interface CommandPublish {

  suspend fun <C : Command<CommandResult>, CommandResult> command(command: C): CommandResult

  fun registryUseCase(commandName: String, usecase: UseCase<*, *>)
}
