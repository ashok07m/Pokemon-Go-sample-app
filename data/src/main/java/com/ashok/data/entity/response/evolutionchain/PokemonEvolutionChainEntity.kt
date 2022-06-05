package com.ashok.data.entity.response.evolutionchain


import com.google.gson.annotations.SerializedName

data class PokemonEvolutionChainEntity(
    @SerializedName("baby_trigger_item")
    val babyTriggerItem: Any? = null,
    @SerializedName("chain")
    val chain: Chain? = null,
    @SerializedName("id")
    val id: Int
)