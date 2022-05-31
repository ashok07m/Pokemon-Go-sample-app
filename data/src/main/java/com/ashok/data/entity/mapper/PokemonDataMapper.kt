package com.ashok.data.entity.mapper

import com.ashok.data.entity.PokemonEntity
import com.ashok.domain.entity.PokemonModel


fun PokemonEntity.toPokemonModel(imgUrl: String): PokemonModel {
    return PokemonModel(
        name = this.name ?: "",
        imgUrl = imgUrl,
        moreInfoUrl = this.url ?: ""
    )
}