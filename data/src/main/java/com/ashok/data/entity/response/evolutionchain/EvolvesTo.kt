package com.ashok.data.entity.response.evolutionchain


import com.google.gson.annotations.SerializedName

data class EvolvesTo(
    @SerializedName("evolution_details")
    val evolutionDetails: List<EvolutionDetail>? = null,
    @SerializedName("evolves_to")
    val evolvesTo: List<Any>? = null,
    @SerializedName("is_baby")
    val isBaby: Boolean? = null,
    @SerializedName("species")
    val species: Species? = null
)