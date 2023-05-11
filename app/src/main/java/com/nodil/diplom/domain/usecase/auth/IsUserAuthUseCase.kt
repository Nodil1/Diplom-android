package com.nodil.diplom.domain.usecase.auth

import com.nodil.diplom.data.repositories.SharedPreferencesStorage

class IsUserAuthUseCase(
    private val sharedPreferencesStorage: SharedPreferencesStorage
) {
    fun execute(): Boolean {
        return sharedPreferencesStorage.contains("token")
    }
}