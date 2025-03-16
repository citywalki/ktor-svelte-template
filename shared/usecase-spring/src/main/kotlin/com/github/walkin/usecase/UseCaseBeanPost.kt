package com.github.walkin.usecase

import java.lang.reflect.ParameterizedType
import java.lang.reflect.TypeVariable
import org.springframework.beans.factory.config.BeanPostProcessor

class UseCaseBeanPost(val commandPublish: CommandPublish) : BeanPostProcessor {
  override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
    if (bean is UseCase<*, *>) {
      commandPublish.registryUseCase(getCommandClass(bean.getUsecaseType()).name, bean)
    }
    return super.postProcessAfterInitialization(bean, beanName)
  }

  private fun getCommandClass(currentClass: Class<*>): Class<*> {
    val superclassType = currentClass.getGenericSuperclass()

    if (superclassType is ParameterizedType) {
      val parameterizedType = superclassType

      val type = parameterizedType.actualTypeArguments[0]
      if (type is Class<*>) {
        return type as Class<*>
      }
      if (type is TypeVariable<*>) {
        val typeVariable = type
        return typeVariable.bounds[0] as Class<*>
      }
    } else if (superclassType is Class<*>) {
      return getCommandClass(superclassType)
    }

    throw IllegalArgumentException()
  }
}
