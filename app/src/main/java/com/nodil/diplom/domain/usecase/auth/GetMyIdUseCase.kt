package com.nodil.diplom.domain.usecase.auth

import com.nodil.diplom.data.repositories.SharedPreferencesStorage

class GetMyIdUseCase(
    private val sharedPreferencesStorage: SharedPreferencesStorage
) {
    fun execute(): Int {
        return sharedPreferencesStorage.getString("id").toInt()
    }
}