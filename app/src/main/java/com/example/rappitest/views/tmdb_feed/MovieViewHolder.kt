package com.example.rappitest.views.tmdb_feed

import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import com.example.rappitest.models.Movie
import com.example.rappitest.utils.PalleteRequestListener
import com.example.rappitest.views.tmdb_detail.TmdbDetailActivity
import com.example.rappitest.views.tmdb_detail.TmdbDetailActivity.Companion.MOVIE_EXTRA
import kotlinx.android.synthetic.main.movie_item_view.view.*


class MovieViewHolder(itemView: View, private val genresMap: Map<Int, String>) : BaseViewHolder(itemView) {

    private lateinit var movie: Movie

    init {
        itemView.setOnClickListener {
            val detailIntent = Intent(it.context, TmdbDetailActivity::class.java)
            detailIntent.putExtra(MOVIE_EXTRA, movie.id)
            it.context.startActivity(detailIntent)
        }
    }

    override fun bind(movie: Movie) {
        this.movie = movie
        itemView.movie_title.text = movie.title

        var genres = ""
        movie.genre_ids?.forEach { genres += genresMap[it] + ", " }
        itemView.movie_genre.text = genres.dropLast(2).toUpperCase() // remove de last ", "
        val uri = Uri.parse(movie.getPosterUrl())
        Glide.with(itemView)
            .load(uri)
            .listener(PalleteRequestListener(itemView.movie_image, PorterDuff.Mode.OVERLAY))
            .into(itemView.movie_image)
    }
}