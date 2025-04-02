package com.github.walkin.memos.usecase.memo

import com.github.walkin.memos.domain.SetMemoResources
import com.github.walkin.usecase.UseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SetMemoResourcesUsecase : UseCase<SetMemoResources, Unit>() {
  override fun handle(command: SetMemoResources) {
    TODO("Not yet implemented")
  }

  override fun getCommandType() = SetMemoResources::class
}
