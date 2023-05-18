package com.nodil.diplom.domain.repositories

interface TaskAttachmentRepository {
    suspend fun saveImage(byteArray: Array<ByteArray>, idTask: Int, onLoad: (progress: Int) -> Unit)
    suspend fun saveSign(byteArray: ByteArray, idTask: Int, onLoad: (progress: Int) -> Unit)
}