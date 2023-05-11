package com.nodil.diplom.data.repositories

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nodil.diplom.Config

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.util.*
import org.koin.java.KoinJavaComponent

abstract class BaseRepository(
    val path: String
) {
    val gsonConverter = createGson()
    private val userStorage: SharedPreferencesStorage by KoinJavaComponent.inject(
        SharedPreferencesStorage::class.java
    )

    val client = HttpClient(Android) {
        engine {
            connectTimeout = 100_000
        }
        defaultRequest {
            url(Config.API_URL + "$path")
            headers.appendIfNameAbsent(
                HttpHeaders.ContentType,
                ContentType.Application.Json.toString()
            )
            if (userStorage.contains("token")) {

                headers.appendIfNameAbsent(
                    HttpHeaders.Authorization,
                    "Bearer ${userStorage.getString("token")}"
                )
            }
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        install(ContentNegotiation) {
            gson {
            }
        }

    }


    private fun createGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()!!

    }

}