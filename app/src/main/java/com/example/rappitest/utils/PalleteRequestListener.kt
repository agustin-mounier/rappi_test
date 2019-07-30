package com.example.rappitest.utils

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class PalleteRequestListener(private val view: ImageView, private val mode: PorterDuff.Mode) : RequestListener<Drawable> {
    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        return false
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        if (resource != null) {
            val palette = Palette.from(resource.toBitmap()).generate()
            val color = palette.getDarkMutedColor(Color.TRANSPARENT)
            view.setColorFilter(color, mode)
        }
        return false
    }
}