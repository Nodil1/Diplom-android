package com.nodil.diplom.domain.usecase.action

import com.nodil.diplom.domain.models.WorkerActionModel
import com.nodil.diplom.domain.repositories.WorkerActionRepository

class SaveWorkerActionUseCase(
    private val repository: WorkerActionRepository
) {
    suspend fun execute(action: WorkerActionModel) = repository.saveAction(action)
}