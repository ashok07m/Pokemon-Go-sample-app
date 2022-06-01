package com.ashok.data.util

import android.util.Log
import com.ashok.data.source.api.PokemonApiService

object DataUtil {

    fun getPokemonIdFromUrl(url: String?): Int {
        return try {
            url?.let {
                val id = url.split("/").takeLast(2).first()
                id.toInt()
            } ?: 0
        } catch (e: Exception) {
            Log.e("TAG", "exception : ${e.localizedMessage}")
            0
        }
    }

    fun getPokemonImageUrlForId(id: Int) = String.format(PokemonApiService.POKEMON_IMAGE_URL, id)

}