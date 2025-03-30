package com.github.walkin.memos.domain

import com.github.walkin.memos.entity.Memo
import com.github.walkin.memos.entity.MemoRelationType
import com.github.walkin.memos.entity.MemoResource
import com.github.walkin.memos.entity.MemosVisibility
import com.github.walkin.usecase.Command

data class CreateMemo(
  val creatorId: Long,
  val content: String,
  val visibility: MemosVisibility? = null,
  val resources: List<MemoResource> = emptyList(),
  val relations: List<MemoRelationType> = emptyList(),
) : Command<Memo>()

data class SetMemoResources(val memoName: String, val resources: List<MemoResource> = emptyList()) :
  Command<Unit>()

data class SetMemoRelations(
  val memoName: String,
  val relations: List<MemoRelationType> = emptyList(),
) : Command<Unit>()
