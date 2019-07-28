package com.example.rappitest.views

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rappitest.R
import com.example.rappitest.repository.remote.TmdbService
import com.example.rappitest.viewmodels.TmdbFeedViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.tmdb_feed_layout.*
import javax.inject.Inject


class TmdbFeedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var scrollListener: InfiniteScrollViewListener

    private val viewModel: TmdbFeedViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[TmdbFeedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tmdb_feed_layout)
        setSupportActionBar(toolbar)
        initFeed()
        initObservers()
        viewModel.fetchMostPopular()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (movie_feed.adapter as MovieFeedAdapter).filter.filter(newText)
                return false
            }
        })
        return true
    }


    fun fetchMostPopular(view: View) {
        viewModel.fetchMostPopular()
    }

    fun fetchTopRated(view: View) {
        viewModel.fetchTopRated()
    }

    fun fetchUpcoming(view: View) {
        viewModel.fetchUpcoming()
    }

    private fun initFeed() {
        val layoutManager = LinearLayoutManager(this)
        scrollListener = InfiniteScrollViewListener(layoutManager, viewModel::loadMoreMovies)

        movie_feed.layoutManager = layoutManager
        movie_feed.addOnScrollListener(scrollListener)
        movie_feed.adapter = MovieFeedAdapter(viewModel.getMovies(), viewModel.getMovieGenres())
    }

    private fun initObservers() {
        viewModel.isLoading().observe(this, Observer {
            if (it) showLoading() else hideLoading()
        })

        viewModel.getMovies().observe(this, Observer {
            val positionStart = it.size - TmdbService.PageSize
            movie_feed.adapter?.notifyItemRangeInserted(positionStart, it.size)
            if (positionStart == 0) movie_feed.scheduleLayoutAnimation() // TODO: ver que onda
        })
    }

    private fun showLoading() {
        loading_spinner.visibility = View.VISIBLE
        loading_text.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading_spinner.visibility = View.GONE
        loading_text.visibility = View.GONE
    }
}