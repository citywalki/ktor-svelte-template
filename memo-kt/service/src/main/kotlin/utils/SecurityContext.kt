package utils

import domain.UserId
import domain.UserName

data class SecurityInfo(val userId: UserId? = null, val username: UserName?= null)

val securityInfoThreadLocal = ThreadLocal<SecurityInfo>()

object SecurityContextHolder {
  fun get(): SecurityInfo {
    return securityInfoThreadLocal.get()
      ?: throw AssertionError("No ApplicationCall in context, is CallContextPlugin installed?")
  }

  fun set(securityInfo: SecurityInfo) {
    securityInfoThreadLocal.set(securityInfo)
  }
}
