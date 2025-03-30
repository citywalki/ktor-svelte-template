package com.github.walkin.memos.domain

import com.github.walkin.memos.entity.Inbox
import com.github.walkin.memos.entity.InboxStatus
import com.github.walkin.usecase.Command
import kotlinx.serialization.Serializable

@Serializable data class UpdateInbox(val id: Long, val status: InboxStatus) : Command<Inbox>()

@Serializable data class DeleteInbox(val id: Long) : Command<Unit>()

data class ListInboxesResponse(val inboxEntities: List<Inbox>, val nextPageToken: PageToken)
