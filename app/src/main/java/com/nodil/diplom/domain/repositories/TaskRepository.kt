package com.nodil.diplom.domain.repositories

import com.nodil.diplom.domain.models.TaskModel

interface TaskRepository {
    suspend fun getMyTasks(): Array<TaskModel>
    fun subscribe(myId: Int, onNewTask: (TaskModel) -> Unit)
}