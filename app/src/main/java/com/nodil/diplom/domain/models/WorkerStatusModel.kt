package com.nodil.diplom.domain.models

import com.nodil.diplom.domain.enums.WorkerStatus

data class WorkerStatusModel(
    var status: WorkerStatus,
    val task: TaskModel? = null
)