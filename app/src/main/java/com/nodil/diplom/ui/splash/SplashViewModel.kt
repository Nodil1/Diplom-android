package com.nodil.diplom.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nodil.diplom.domain.usecase.auth.IsUserAuthUseCase

class SplashViewModel(
    private val isUserAuthUseCase: IsUserAuthUseCase
): ViewModel()
{
    private val _authState =  MutableLiveData<Boolean?>(null)
    val authState : LiveData<Boolean?> get() = _authState

    fun load(){
       _authState.postValue(isUserAuthUseCase.execute())
    }

}