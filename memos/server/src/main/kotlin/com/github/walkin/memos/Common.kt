package com.github.walkin.memos

import com.github.walkin.memos.entity.Memo
import com.github.walkin.memos.store.*
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser
import org.komapper.core.dsl.Meta
import org.springframework.web.bind.annotation.RestController

const val DefaultPageSize: Int = 10

@RestController @Retention(AnnotationRetention.RUNTIME) annotation class MemosController

object Entity {
  val user = Meta.user
  val userSpace = Meta.userSpace
  val userSetting = Meta.userSetting
  val workspaceSetting = Meta.workspaceSetting
  val inbox = Meta.inbox
  val memo = Meta.memo
}

fun Memo.rebuildMemoPayload() {
  val flavour = CommonMarkFlavourDescriptor()
  val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(this.content)

  for (node in parsedTree.children) {}
}
