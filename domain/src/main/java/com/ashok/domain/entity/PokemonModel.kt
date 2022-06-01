package com.ashok.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonModel(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val moreInfoUrl: String
) : Parcelable