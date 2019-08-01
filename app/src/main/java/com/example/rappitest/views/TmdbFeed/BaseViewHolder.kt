package com.example.rappitest.views.TmdbFeed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.rappitest.models.Movie

abstract class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(movie: Movie)
}