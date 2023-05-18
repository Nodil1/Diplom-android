package com.nodil.diplom.domain.usecase.attachment

import com.nodil.diplom.domain.repositories.TaskAttachmentRepository

class SaveImageAttachmentUseCase(
    private val repository: TaskAttachmentRepository
) {
    suspend fun execute(byteArray: Array<ByteArray>, idTask: Int, onLoad: (progress: Int) -> Unit) =
        repository.saveImage(byteArray, idTask, onLoad)
}