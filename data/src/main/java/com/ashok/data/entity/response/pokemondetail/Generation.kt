package com.ashok.data.entity.response.pokemondetail


import com.google.gson.annotations.SerializedName

data class Generation(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)