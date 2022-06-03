package com.ashok.data.entity.response.evolutionchain


import com.google.gson.annotations.SerializedName

data class SpeciesX(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)