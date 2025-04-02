package com.github.walkin.usecase

class CommandPublishImpl : CommandPublish {
  private val usecaseMap: MutableMap<String, MutableCollection<UseCase<out Command<*>, *>>> =
    HashMap()

  override fun <C : Command<CommandResult>, CommandResult> command(command: C): CommandResult {
    return command(command) { it }
  }

  fun <T : Command<CommandResult>, CommandResult, Result> command(
    command: T,
    block: (CommandResult) -> Result,
  ): Result {
    return select(command)
      .filter { it.condition(command) }
      .minByOrNull { it.priority() }!!
      .handle(command)
      .let { block.invoke(it) }
  }

  private fun <C : Command<R>, R> select(command: C): MutableCollection<UseCase<C, R>> {
    val useCases = usecaseMap[command::class.qualifiedName]
    if (useCases.isNullOrEmpty()) {
      throw IllegalArgumentException("No usecase found for ${command::class}")
    }
    @Suppress("UNCHECKED_CAST")
    return useCases as MutableCollection<UseCase<C, R>>
  }

  override fun registryUseCase(commandName: String, usecase: UseCase<*, *>) {
    val key = commandName
    if (!usecaseMap.containsKey(key)) {
      usecaseMap[key] = HashSet()
    }
    usecaseMap[key]!!.add(usecase)
  }
}
