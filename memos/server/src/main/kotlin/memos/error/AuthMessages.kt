package memos.error

import pro.walkin.logging.annotations.Message
import pro.walkin.logging.annotations.MessageBundle

@MessageBundle("auth")
interface AuthMessages {

    @Message("Version %d.%d.%d.%s")
    fun version(major: Int, minor: Int, macro: Int, rel: String): String

    @Message("Permission denied")
    fun permissionDenied(): String
}
