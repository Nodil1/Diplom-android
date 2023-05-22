package com.nodil.diplom.data.repositories

import com.nodil.diplom.domain.repositories.TaskAttachmentRepository
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*

class TaskAttachmentRepositoryApi : BaseRepository("taskAttachment/"), TaskAttachmentRepository {


    override suspend fun saveImage(
        byteArray: Array<ByteArray>,
        idTask: Int,
        onLoad: (progress: Int) -> Unit
    ) {
        val request = client.post("images") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("idTask", idTask)
                        for (bytes in byteArray) {
                            append("photos[]", bytes, Headers.build {
                                append(HttpHeaders.ContentType, "image/png")
                                append(HttpHeaders.ContentDisposition, "filename=\"ktor_logo.png\"")
                            })
                        }
                    },
                )
            )
            onUpload { bytesSentTotal, contentLength ->
                onLoad((bytesSentTotal.toDouble() / contentLength.toDouble() * 100).toInt())
            }
        }
    }

    override suspend fun saveSign(byteArray: ByteArray, idTask: Int, onLoad: (progress: Int) -> Unit) {
        val request = client.post("sign") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("idTask", idTask)
                        append("photo", byteArray, Headers.build {
                            append(HttpHeaders.ContentType, "image/png")
                            append(HttpHeaders.ContentDisposition, "filename=\"ktor_logo.png\"")
                        })

                    },
                )
            )
            onUpload { bytesSentTotal, contentLength ->
                onLoad((bytesSentTotal.toDouble() / contentLength.toDouble() * 100).toInt())
                println("Sent $bytesSentTotal bytes from $contentLength Progress ${bytesSentTotal.toDouble() / contentLength.toDouble()}")
            }

        }
    }

}