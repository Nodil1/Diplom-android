package com.nodil.diplom.domain.usecase.auth

import com.nodil.diplom.data.repositories.SharedPreferencesStorage
import com.nodil.diplom.domain.repositories.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository,
    private val sharedPreferencesStorage: SharedPreferencesStorage
) {
    suspend fun execute(login: String, password: String) {
        val result = authRepository.login(login, password).asJsonObject
        sharedPreferencesStorage.saveString("token", result.get("token").asString)
        sharedPreferencesStorage.saveString("id", result.get("id").asInt.toString())

    }
}