package com.nodil.diplom.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nodil.diplom.domain.usecase.auth.LoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _login = MutableLiveData<String>()
    val login: LiveData<String>
        get() = _login

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    private val _isLoginSuccessful = MutableLiveData<Boolean>()
    val isLoginSuccessful: LiveData<Boolean>
        get() = _isLoginSuccessful

    fun setLogin(login: String) {
        _login.value = login
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun login() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                loginUseCase.execute(_login.value!!, _password.value!!)
                _isLoginSuccessful.postValue(true)
            } catch (exception: Exception) {
                _isLoginSuccessful.postValue(false)
                exception.printStackTrace()
            }
        }
    }
}