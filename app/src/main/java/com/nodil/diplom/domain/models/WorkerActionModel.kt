package com.nodil.diplom.domain.models

data class WorkerActionModel(
    val type: Int,
    val idWorker: Int = 0,
    val idTask: Int? = null,
    val id: Int? = null

)