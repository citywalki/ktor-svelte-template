package com.github.walkin.memos

import com.github.walkin.memos.entity.*
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser
import org.springframework.web.bind.annotation.RestController

const val DefaultPageSize: Int = 10

@RestController @Retention(AnnotationRetention.RUNTIME) annotation class MemosController

fun Memo.rebuildMemoPayload() {
  val flavour = CommonMarkFlavourDescriptor()
  val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(this.content)

  for (node in parsedTree.children) {}
}
