package com.github.walkin.memos

import com.github.walkin.memos.entity.*
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser

const val DefaultPageSize: Int = 10

fun Memo.rebuildMemoPayload() {
  val flavour = CommonMarkFlavourDescriptor()
  val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(this.content)

  for (node in parsedTree.children) {}
}
