package com.nodil.diplom.data.repositories

import com.nodil.diplom.domain.models.WorkerActionModel
import com.nodil.diplom.domain.repositories.WorkerActionRepository
import io.ktor.client.request.*
import io.ktor.client.statement.*

class WorkerActionRepositoryApi: BaseRepository("workerAction"), WorkerActionRepository {
    override suspend fun saveAction(action: WorkerActionModel) {
        val response = client.post {
            setBody(action)
        }
        println(response.bodyAsText())

    }
}