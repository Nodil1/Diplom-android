package com.nodil.diplom.ui.home.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nodil.diplom.domain.enums.WorkerAction
import com.nodil.diplom.domain.enums.WorkerStatus
import com.nodil.diplom.domain.models.WorkerActionModel
import com.nodil.diplom.domain.usecase.action.SaveWorkerActionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatusViewModel(
    private val saveWorkerActionUseCase: SaveWorkerActionUseCase
): ViewModel() {

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int> get() = _status

    fun changeStatus(status: Int) {
        _status.value = status
        when(WorkerStatus.fromInt(status)) {
            WorkerStatus.WORKING -> {
                saveAction(WorkerActionModel(WorkerAction.START_WORK.ordinal))
            }
            else -> {}
        }

    }

    fun saveAction(workerActionModel: WorkerActionModel) {
        CoroutineScope(Dispatchers.IO).launch {
            saveWorkerActionUseCase.execute(workerActionModel)
        }
    }
}