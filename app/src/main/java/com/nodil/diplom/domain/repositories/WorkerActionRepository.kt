package com.nodil.diplom.domain.repositories

import com.nodil.diplom.domain.models.WorkerActionModel

interface WorkerActionRepository {
    suspend fun saveAction(action: WorkerActionModel)
}