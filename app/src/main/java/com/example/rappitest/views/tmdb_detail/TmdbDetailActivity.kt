package com.example.rappitest.views.tmdb_detail

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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
    private var movieId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tmdb_detail_layout)
        movieId = intent.getIntExtra(MOVIE_EXTRA, -1)
        viewModel.fetchMovieVideos(movieId)
        val movie = viewModel.getMovie(movieId)
        movie?.let { setUpView(it) }
        initMoviePreviews()
    }

    private fun setUpView(movie: Movie) {
        val glideManager = Glide.with(this)
        glideManager.load(movie.getPosterUrl())
            .listener(PalleteRequestListener(movie_detail_backdrop, PorterDuff.Mode.MULTIPLY))
            .transform(RoundedCorners(20))
            .into(movie_detail_poster)

        movie.getBackDropPath(this)?.let {
            glideManager.load(it).centerCrop().into(movie_detail_backdrop)
        }

        movie_detail_title.text = movie.title
        movie_detail_year.text = movie.release_date!!.substring(0, 4)
        movie_overview.text = movie.overview
    }

    private fun initMoviePreviews() {
        val movieVideos = viewModel.getMovieVideos(movieId)
        val layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        videos_list.layoutManager = layoutManager
        videos_list.adapter = VideosListAdapter(movieVideos)
        movieVideos.observe(this, Observer {
            if (it.isNotEmpty()) {
                movie_previews.visibility = View.VISIBLE
                (videos_list.adapter as VideosListAdapter).notifyDataSetChanged()
            }
        })
    }
}