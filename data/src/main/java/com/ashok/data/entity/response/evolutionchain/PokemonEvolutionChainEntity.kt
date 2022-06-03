package com.ashok.data.entity.response.evolutionchain


import com.google.gson.annotations.SerializedName

data class PokemonEvolutionChainEntity(
    @SerializedName("baby_trigger_item")
    val babyTriggerItem: Any?,
    @SerializedName("chain")
    val chain: Chain?,
    @SerializedName("id")
    val id: Int
)