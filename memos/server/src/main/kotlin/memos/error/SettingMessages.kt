package memos.error

import pro.walkin.logging.annotations.Message
import pro.walkin.logging.annotations.MessageBundle

@MessageBundle(projectCode = "setting")
interface SettingMessages {
    @Message("disable public memos system setting is enabled")
    fun publicVisibilityDenied(): String?

    @Message("content too long (max %d characters)")
    fun contentMaxLimitDenied(maxLength: Int): String?
}
