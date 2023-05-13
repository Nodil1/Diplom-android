package com.nodil.diplom.domain.usecase.action

import com.nodil.diplom.domain.repositories.WorkerActionRepository

class GetMyWorkerStatusUseCase(
    private val workerActionRepository: WorkerActionRepository
) {
    suspend fun execute() = workerActionRepository.getMyStatus()
}