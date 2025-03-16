package com.github.walkin.memos

object MemosExceptionFactory {
  fun permissionDenied() = IllegalStateException("Permission denied")

  object User {
    fun userSameExist(): IllegalStateException = IllegalStateException("已经存在相同用户")
  }

  object Memo {
    fun publicVisibilityDenied() =
      IllegalStateException("disable public memos system setting is enabled")

    fun contentMaxLimitDenied(maxLength: Int) =
      IllegalStateException("content too long (max $maxLength characters)")
  }
}
