package com.ashok.pokemongo.ui.mapper

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonDetail(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val moreInfoUrl: String,
    val captureRate: Int,
    val flavorText: String
) : Parcelable
