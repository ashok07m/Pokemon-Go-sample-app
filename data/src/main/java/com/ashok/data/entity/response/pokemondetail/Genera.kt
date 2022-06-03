package com.ashok.data.entity.response.pokemondetail


import com.google.gson.annotations.SerializedName

data class Genera(
    @SerializedName("genus")
    val genus: String?,
    @SerializedName("language")
    val language: LanguageX?
)