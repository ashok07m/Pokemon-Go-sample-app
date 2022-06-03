package com.ashok.pokemongo.ui.mapper

import com.ashok.domain.entity.PokemonDetailModel

fun PokemonDetailModel.toPokemonDetail(flavorText: String): PokemonDetail {
    return PokemonDetail(
        id = this.id,
        name = this.name,
        imgUrl = this.imgUrl,
        moreInfoUrl = this.moreInfoUrl,
        captureRate = this.captureRate,
        flavorText = flavorText
    )
}