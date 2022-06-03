package com.ashok.data.entity.response.pokemondetail


import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("language")
    val language: LanguageXX?,
    @SerializedName("name")
    val name: String?
)