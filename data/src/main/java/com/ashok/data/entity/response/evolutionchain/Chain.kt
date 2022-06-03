package com.ashok.data.entity.response.evolutionchain


import com.google.gson.annotations.SerializedName

data class Chain(
    @SerializedName("evolution_details")
    val evolutionDetails: List<Any>?,
    @SerializedName("evolves_to")
    val evolvesTo: List<EvolvesTo>?,
    @SerializedName("is_baby")
    val isBaby: Boolean?,
    @SerializedName("species")
    val species: SpeciesX?
)