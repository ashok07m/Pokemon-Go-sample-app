package com.ashok.data.entity

import com.google.gson.annotations.SerializedName

data class PokemonEntity(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<Result>?
)