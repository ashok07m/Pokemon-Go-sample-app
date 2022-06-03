package com.ashok.data.entity.response.evolutionchain


import com.google.gson.annotations.SerializedName

data class EvolvesTo(
    @SerializedName("evolution_details")
    val evolutionDetails: List<EvolutionDetail>?,
    @SerializedName("evolves_to")
    val evolvesTo: List<Any>?,
    @SerializedName("is_baby")
    val isBaby: Boolean?,
    @SerializedName("species")
    val species: Species?
)