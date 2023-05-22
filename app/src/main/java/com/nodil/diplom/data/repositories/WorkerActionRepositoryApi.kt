package com.nodil.diplom.data.repositories

import com.google.gson.JsonObject
import com.nodil.diplom.domain.enums.WorkerStatus
import com.nodil.diplom.domain.models.WorkerActionModel
import com.nodil.diplom.domain.models.WorkerStatusModel
import com.nodil.diplom.domain.repositories.WorkerActionRepository
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class WorkerActionRepositoryApi: BaseRepository("workerAction/"), WorkerActionRepository {
    override suspend fun saveAction(action: WorkerActionModel) {
        val response = client.post {
            setBody(action)
        }

    }

    override suspend fun getMyStatus(): WorkerStatusModel {
        val response = client.get("myStatus") {

        }
        val responseBody = response.body<JsonObject>()
        val model =response.body<WorkerStatusModel>()
        model.status = WorkerStatus.fromInt(responseBody.get("status").asInt)
        return model
    }
}