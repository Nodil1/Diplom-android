package com.nodil.diplom.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nodil.diplom.domain.enums.WorkerAction
import com.nodil.diplom.domain.enums.WorkerStatus
import com.nodil.diplom.domain.models.TaskModel
import com.nodil.diplom.domain.models.WorkerActionModel
import com.nodil.diplom.domain.usecase.action.GetMyWorkerStatusUseCase
import com.nodil.diplom.domain.usecase.action.SaveWorkerActionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val saveWorkerActionUseCase: SaveWorkerActionUseCase,
    private val getMyWorkerStatusUseCase: GetMyWorkerStatusUseCase
): ViewModel() {
    private val _currentPage = MutableLiveData(0)
    val currentPage : LiveData<Int> get() = _currentPage
    init {
        println("INIT")
        CoroutineScope(Dispatchers.IO).launch {
            val status = getMyWorkerStatusUseCase.execute()
            println(status)
            if (status.status != WorkerStatus.NOT_WORKING && status.status !== WorkerStatus.WORKING){
                _status.postValue(WorkerStatus.WORKING)
                println("Enable")
                delay(1000
                )
            }
            _status.postValue(status.status)
            _currentTask.postValue(status.task)
        }
    }

    fun setPage(page: Int){
        _currentPage.value = page
    }

    private val _status = MutableLiveData<WorkerStatus>()
    val status: LiveData<WorkerStatus> get() = _status

    private val _currentTask = MutableLiveData<TaskModel?>(null)
    val currentTask: LiveData<TaskModel?> get() = _currentTask

    fun changeStatus(status: WorkerStatus, idTask: Int? = null) {

        when(status) {
            WorkerStatus.WORKING -> {
                saveAction(WorkerActionModel(WorkerAction.START_WORK.ordinal))
            }
            WorkerStatus.GO_TO_TASK -> {
                if (_status.value == WorkerStatus.WORKING){
                    saveAction(WorkerActionModel(WorkerAction.TAKE_TASK.ordinal, idTask=idTask) )
                }
            }
            WorkerStatus.DO_TASK -> {
                saveAction(WorkerActionModel(WorkerAction.START_TASK.ordinal, idTask=idTask) )
            }
            else -> {}
        }
        _status.value = status

    }
    fun setCurrentTask(taskModel: TaskModel){
        _currentTask.value = taskModel
    }
    fun saveAction(workerActionModel: WorkerActionModel) {
        CoroutineScope(Dispatchers.IO).launch {
            saveWorkerActionUseCase.execute(workerActionModel)
        }
    }
}