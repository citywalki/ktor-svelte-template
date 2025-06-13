package memos.error

import domain.UserName
import pro.walkin.logging.annotations.Message
import pro.walkin.logging.annotations.MessageBundle

@MessageBundle("auth")
interface AuthMessages {

    @Message("Version %d.%d.%d.%s")
    fun version(major: Int, minor: Int, macro: Int, rel: String): String

    @Message(code = 1, value = "Permission denied")
    fun permissionDenied(): IllegalStateException

    @Message(code = 2, value = "不允许密码登录")
    fun passwordSigninNotAllowed(): IllegalStateException

    @Message(code = 3, value = "user has been archived with username %s")
    fun userHasBeenArchived(userName: UserName): IllegalStateException
}
