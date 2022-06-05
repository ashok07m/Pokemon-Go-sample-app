package com.ashok.data.entity.response.evolutionchain


import com.google.gson.annotations.SerializedName

data class Chain(
    @SerializedName("evolution_details")
    val evolutionDetails: List<Any>? = null,
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolvesTo>? = null,
    @SerializedName("is_baby")
    val isBaby: Boolean? = null,
    @SerializedName("species")
    val species: SpeciesX? = null
)