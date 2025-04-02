package com.github.walkin.usecase

interface CommandPublish {

  fun <C : Command<CommandResult>, CommandResult> command(command: C): CommandResult

  fun registryUseCase(commandName: String, usecase: UseCase<*, *>)
}
