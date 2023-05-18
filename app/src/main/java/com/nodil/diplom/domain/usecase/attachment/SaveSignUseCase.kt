package com.nodil.diplom.domain.usecase.attachment

import com.nodil.diplom.domain.repositories.TaskAttachmentRepository

class SaveSignUseCase(
    private val repository : TaskAttachmentRepository
) {
    suspend fun execute(byteArray: ByteArray, idTask: Int, onLoad: (progress: Int) -> Unit) =
        repository.saveSign(byteArray, idTask, onLoad)
}