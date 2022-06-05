package com.ashok.data.entity.response.pokemoncollection

import com.google.gson.annotations.SerializedName

data class PokemonEntity(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: String? = null,
    @SerializedName("results")
    val results: List<Result>? = null
)