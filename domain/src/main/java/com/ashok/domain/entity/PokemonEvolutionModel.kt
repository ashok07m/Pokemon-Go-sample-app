package com.ashok.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonEvolutionModel(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val moreInfoUrl: String,
    var captureRate: Int = 0
) : Parcelable