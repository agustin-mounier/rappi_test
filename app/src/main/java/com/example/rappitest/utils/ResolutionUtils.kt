package com.example.rappitest.utils

import android.content.Context

object ResolutionUtils {

    const val LDPI = 0.75f
    const val MDPI = 1.0f
    const val HDPI = 1.5f
    const val XHDPI = 2.0f
    const val XXHDPI = 3.0f
    const val XXXHDPI = 4.0f

    fun getBackDropResolution(context: Context): String {
        val density =  context.resources.displayMetrics.density
        return when (density) {
            LDPI, MDPI -> {"w300"}
            HDPI, XHDPI -> {"w780"}
            XXHDPI, XXXHDPI -> {"w1280"}
            else -> "w780"
        }
    }
}