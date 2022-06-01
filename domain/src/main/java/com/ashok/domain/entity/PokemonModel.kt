package com.ashok.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonModel(
    val name: String,
    val imgUrl: String,
    val moreInfoUrl: String
) : Parcelable