package com.example.barbara.rickandmorty

import android.net.Uri
import okhttp3.OkHttpClient
import okhttp3.Request

object NetworkUtils {

    private const val BASE_CHARACTER_URL = "https://rickandmortyapi.com/api/character"
    private const val PARAM_PAGE = "page"
    fun getApiCharacterResponse(page: Int) : String? {
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url(buildRequestURL(page))
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

    private fun buildRequestURL(page: Int) : String {
        return Uri.parse(BASE_CHARACTER_URL).buildUpon()
            .appendQueryParameter(PARAM_PAGE, page.toString())
            .build()
            .toString()
    }
}