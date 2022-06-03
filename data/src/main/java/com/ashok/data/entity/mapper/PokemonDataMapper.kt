package com.ashok.data.entity.mapper

import com.ashok.data.entity.response.evolutionchain.PokemonEvolutionChainEntity
import com.ashok.data.entity.response.pokemoncollection.Result
import com.ashok.data.entity.response.pokemondetail.PokemonDetailEntity
import com.ashok.data.util.DataUtil
import com.ashok.domain.entity.PokemonDetailModel
import com.ashok.domain.entity.PokemonEvolutionModel
import com.ashok.domain.entity.PokemonModel

fun Result.toPokemonModel(): PokemonModel {
    val id = DataUtil.getPokemonIdFromUrl(this.url)
    val url = DataUtil.getPokemonImageUrlForId(id)
    return PokemonModel(
        id = id,
        name = this.name ?: "",
        imgUrl = url,
        moreInfoUrl = this.url ?: ""
    )
}

fun PokemonDetailEntity.toPokemonDetailModel(): PokemonDetailModel {
    val descriptionList =
        this.flavorTextEntries?.filter { !it.flavorText.isNullOrEmpty() } ?: emptyList()
    val url = DataUtil.getPokemonImageUrlForId(this.id)
    val infoUrl = DataUtil.getPokemonInfoUrlForId(this.id)
    return PokemonDetailModel(
        id = this.id ?: 0,
        name = this.name ?: "",
        imgUrl = url,
        moreInfoUrl = infoUrl,
        captureRate = this.captureRate ?: 0,
        flavorText = descriptionList?.map { it.flavorText!! }
    )
}

fun PokemonEvolutionChainEntity.toPokemonEvolutionModel(): PokemonEvolutionModel {
    val species = this.chain?.evolvesTo?.firstOrNull()?.species
    val chainId = DataUtil.getPokemonIdFromUrl(species?.url)
    val url = DataUtil.getPokemonImageUrlForId(chainId)
    return PokemonEvolutionModel(
        id = chainId,
        name = species?.name ?: "",
        imgUrl = url,
        moreInfoUrl = species?.url ?: "",
    )
}