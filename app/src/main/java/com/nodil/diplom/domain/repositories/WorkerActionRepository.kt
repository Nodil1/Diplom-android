package com.nodil.diplom.domain.repositories

import com.nodil.diplom.domain.models.WorkerActionModel
import com.nodil.diplom.domain.models.WorkerStatusModel

interface WorkerActionRepository {
    suspend fun saveAction(action: WorkerActionModel)
    suspend fun getMyStatus(): WorkerStatusModel
}