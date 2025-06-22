package app.backend.error

class ArgumentVerificationException(
    val field: String,
    message: String,
) : RuntimeException(message)
