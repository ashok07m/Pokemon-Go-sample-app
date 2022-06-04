package com.ashok.pokemongo.ui.utils

import android.widget.ImageView
import androidx.core.net.toUri
import com.squareup.picasso.Picasso

object AppUtil {
    fun loadImageFromUri(resource: String?, image: ImageView) {
        Picasso.get().load(resource?.toUri()).into(image)
    }
}