package com.example.rappitest.views

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rappitest.models.Movie
import com.example.rappitest.utils.PalleteRequestListener
import kotlinx.android.synthetic.main.movie_item_view.view.*


class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var movie: Movie

    init {
        itemView.setOnClickListener {
        }
    }

    fun bind(movie: Movie, genresMap: Map<Int, String>) {
        this.movie = movie
        itemView.movie_title.text = movie.title

        var genres = ""
        movie.genre_ids?.forEach { genres += genresMap[it] + ", " }
        itemView.movie_genre.text = genres.dropLast(2).toUpperCase() // remove de last ", "
        val uri = Uri.parse(movie.getPosterUrl())
        Glide.with(itemView)
            .load(uri)
            .centerCrop()
            .listener(PalleteRequestListener(itemView.movie_image))
            .into(itemView.movie_image)
    }
}