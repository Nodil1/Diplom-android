package com.nodil.diplom.domain.usecase.task

import com.nodil.diplom.domain.repositories.TaskRepository

class GetMyTasksUseCase(
    private val repository: TaskRepository
) {
    suspend fun execute() = repository.getMyTasks()
}