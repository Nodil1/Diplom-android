package com.nodil.diplom.data.repositories

import com.google.gson.JsonElement
import com.nodil.diplom.domain.repositories.AuthRepository
import io.ktor.client.call.*
import io.ktor.client.request.*


class AuthRepositoryApi : AuthRepository, BaseRepository("auth/") {
    override suspend fun login(login: String, password: String): JsonElement {

        val body =  client.get("") {
            parameter("login", login)
            parameter("password", password)
        }.body() as String
        println(body)
        return gsonConverter.fromJson(body, JsonElement::class.java)
    }

    override suspend fun logout() {

    }
}