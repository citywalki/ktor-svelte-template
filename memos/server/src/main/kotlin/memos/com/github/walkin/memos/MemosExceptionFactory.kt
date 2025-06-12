package memos.com.github.walkin.memos

object MemosExceptionFactory {
  fun permissionDenied() = IllegalStateException("Permission denied")

  object UserExceptions {
    fun userPasswordNotMatch() = IllegalStateException("User password not match")

    fun userSameExist(): IllegalStateException = IllegalStateException("已经存在相同用户")

    fun userNotExist(): IllegalStateException = IllegalStateException("用户不存在")
  }

  object MemoExceptions {
    fun publicVisibilityDenied() =
      IllegalStateException("disable public memos system setting is enabled")

    fun contentMaxLimitDenied(maxLength: Int) =
      IllegalStateException("content too long (max $maxLength characters)")
  }
}
