package com.nodil.diplom.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nodil.diplom.domain.usecase.attachment.SaveImageAttachmentUseCase
import com.nodil.diplom.domain.usecase.attachment.SaveSignUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskAttachmentViewModel(
    private val saveImageAttachmentUseCase: SaveImageAttachmentUseCase,
    private val saveSignUseCase: SaveSignUseCase
): ViewModel() {
    private val _idTask = MutableLiveData<Int>(0)
    val idTask: LiveData<Int> get() = _idTask

    private val _image = MutableLiveData<MutableList<ByteArray>>(mutableListOf())
    val image: LiveData<MutableList<ByteArray>> get() = _image

    private val _sign = MutableLiveData<ByteArray>()
    val sign: LiveData<ByteArray> get() = _sign

    private val _loadingEnd = MutableLiveData(false)
    val loadingEnd: LiveData<Boolean> get() = _loadingEnd

    fun addImage(image: ByteArray) {
        _image.value?.add(image)
        _image.value = _image.value
    }

    fun addSign(sign: ByteArray) {
        _sign.value = sign
    }

    fun setId(id: Int){
        _idTask.value = id
    }
    fun save(){
        CoroutineScope(Dispatchers.IO).launch {
            saveImageAttachmentUseCase.execute(_image.value!!.toTypedArray(), _idTask.value!!){
                println(it)
            }
            saveSignUseCase.execute(_sign.value!!, _idTask.value!!){
                println("Sign $it")
            }
            _loadingEnd.postValue(true)
        }
    }
}