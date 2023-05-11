package com.nodil.diplom.domain.repositories

import com.google.gson.JsonElement

interface AuthRepository {
    suspend fun login(login: String, password: String): JsonElement
    suspend fun logout()
}