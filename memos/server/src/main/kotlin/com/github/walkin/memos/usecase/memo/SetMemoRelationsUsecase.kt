package com.github.walkin.memos.usecase.memo

import com.github.walkin.memos.domain.SetMemoRelations
import com.github.walkin.usecase.UseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SetMemoRelationsUsecase : UseCase<SetMemoRelations, Unit>() {
  override suspend fun handle(command: SetMemoRelations) {
    TODO("Not yet implemented")
  }
}
