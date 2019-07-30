package com.example.rappitest.views

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.rappitest.R
import com.example.rappitest.models.Movie
import com.example.rappitest.utils.PalleteRequestListener
import com.example.rappitest.viewmodels.TmdbDetailViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.tmdb_detail_layout.*
import javax.inject.Inject

class TmdbDetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_EXTRA = "movie_extra"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: TmdbDetailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[TmdbDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tmdb_detail_layout)
        val movieId = intent.getIntExtra(MOVIE_EXTRA, -1)
        val movie = viewModel.getMovie(movieId)
        movie?.let { setUpView(it) }
    }

    private fun setUpView(movie: Movie) {
        Glide.with(this).load(movie.getPosterUrl()).transform(RoundedCorners(20)).into(movie_detail_poster)
        Glide.with(this).load(movie.getBackDropPath()).listener(PalleteRequestListener(movie_detail_backdrop, PorterDuff.Mode.MULTIPLY)).centerCrop().into(movie_detail_backdrop)
        movie_detail_title.text = movie.title
        movie_detail_year.text = movie.release_date!!.substring(0, 4)
        movie_overview.text = movie.overview
    }
}