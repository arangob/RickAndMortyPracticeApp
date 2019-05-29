package com.example.barbara.rickandmorty

import org.json.JSONObject

object JsonUtils {

    fun parseResponse(response: String): List<RickAndMortyCharacter> {
        val characters = ArrayList<RickAndMortyCharacter>()
        val jsonObject = JSONObject(response)
        val charactersArray = jsonObject.getJSONArray("results")
        for (i in 0 until charactersArray.length()) {
            val character: JSONObject = charactersArray.get(i) as JSONObject
            val name = character.getString("name")
            val image = character.getString("image")
            val status = character.getString("status")
            characters.add(RickAndMortyCharacter(name, status, image))
        }

        return characters
    }
}