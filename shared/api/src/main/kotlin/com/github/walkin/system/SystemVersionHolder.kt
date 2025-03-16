package com.github.walkin.system

fun interface SystemVersionHolder {
  fun version(): String
}

class SystemVersionHolderImpl(private val version: String) : SystemVersionHolder {
  override fun version(): String {
    return version
  }
}
