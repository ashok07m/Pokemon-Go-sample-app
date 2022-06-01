package com.ashok.data.entity.mapper

import com.ashok.data.entity.Result
import com.ashok.domain.entity.PokemonModel


fun Result.toPokemonModel(imgUrl: String): PokemonModel {
    return PokemonModel(
        name = this.name ?: "",
        imgUrl = imgUrl,
        moreInfoUrl = this.url ?: ""
    )
}