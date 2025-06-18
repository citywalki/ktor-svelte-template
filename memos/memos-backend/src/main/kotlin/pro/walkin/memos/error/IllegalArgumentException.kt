package pro.walkin.memos.error

class ArgumentVerificationException(
    val field: String,
    message: String,
) : RuntimeException(message)
