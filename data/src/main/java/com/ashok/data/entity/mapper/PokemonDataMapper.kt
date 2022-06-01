package com.ashok.data.entity.mapper

import com.ashok.data.entity.Result
import com.ashok.domain.entity.PokemonModel


fun Result.toPokemonModel(id: Int, imgUrl: String): PokemonModel {
    return PokemonModel(
        id = id,
        name = this.name ?: "",
        imgUrl = imgUrl,
        moreInfoUrl = this.url ?: ""
    )
}