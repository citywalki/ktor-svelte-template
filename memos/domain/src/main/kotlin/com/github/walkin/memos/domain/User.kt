package com.github.walkin.memos.domain

import com.github.walkin.usecase.Command
import kotlinx.serialization.Serializable

// ----------------------- command -----------------------

@Serializable
data class UpdateUser(val userId: String, val name: String, val password: String) : Command<Unit>()
