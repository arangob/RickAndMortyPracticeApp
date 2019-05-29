package com.example.barbara.rickandmorty

import okhttp3.OkHttpClient
import okhttp3.Request

object NetworkUtils {

    private const val BASE_CHARACTER_URL = "https://rickandmortyapi.com/api/character/"

    fun getApiCharacterResponse() : String? {
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_CHARACTER_URL)
            .build()

        try {
            okHttpClient.newCall(request)
                .execute().use { response ->
                    return response.body?.string()
                }
        } catch (e: Exception) {
            return e.toString()
        }
    }
}