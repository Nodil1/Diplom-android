package com.nodil.diplom.ui.home.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nodil.diplom.domain.models.TaskModel
import com.nodil.diplom.domain.usecase.task.GetMyTasksUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(
    private val getMyTasksUseCase: GetMyTasksUseCase
) : ViewModel(){


}