package com.ashok.data.entity.response.evolutionchain


import com.google.gson.annotations.SerializedName

data class Species(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("url")
    val url: String? = null
)