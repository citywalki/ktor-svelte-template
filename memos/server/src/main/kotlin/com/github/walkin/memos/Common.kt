package com.github.walkin.memos

import com.github.walkin.memos.domain.Memo
import com.github.walkin.memos.store.inbox
import com.github.walkin.memos.store.memo
import com.github.walkin.memos.store.user
import com.github.walkin.memos.store.workspaceSettingEntity
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser
import org.komapper.core.dsl.Meta
import org.springframework.web.bind.annotation.RestController

const val DefaultPageSize: Int = 10

@RestController @Retention(AnnotationRetention.RUNTIME) annotation class MemosController

object Entity {
  val user = Meta.user
  val workspaceSetting = Meta.workspaceSettingEntity
  val inbox = Meta.inbox
  val memo = Meta.memo
}

fun Memo.rebuildMemoPayload() {
  val flavour = CommonMarkFlavourDescriptor()
  val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(this.content)

  for (node in parsedTree.children) {}
}
