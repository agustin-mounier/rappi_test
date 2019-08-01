package com.example.rappitest.views.TmdbFeed

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
import com.example.rappitest.repository.remote.ErrorType
import com.example.rappitest.repository.remote.TmdbService
import com.example.rappitest.utils.InfiniteScrollViewListener
import com.example.rappitest.viewmodels.TmdbFeedViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.tmdb_feed_layout.*
import javax.inject.Inject


class TmdbFeedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var scrollListener: InfiniteScrollViewListener
    private var scheduleAnimation = false

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
        scrollListener.resetState()
        scheduleAnimation = true
    }

    fun fetchTopRated(view: View) {
        viewModel.fetchTopRated()
        scrollListener.resetState()
        scheduleAnimation = true
    }

    fun fetchUpcoming(view: View) {
        viewModel.fetchUpcoming()
        scrollListener.resetState()
        scheduleAnimation = true
    }

    private fun initFeed() {
        val layoutManager = LinearLayoutManager(this)
        scrollListener =
            InfiniteScrollViewListener(layoutManager, viewModel::loadMoreMovies)

        movie_feed.layoutManager = layoutManager
        movie_feed.addOnScrollListener(scrollListener)
        movie_feed.adapter =
            MovieFeedAdapter(
                viewModel.getMovies(),
                viewModel.getMovieGenres(),
                viewModel.isLoadingPage()
            )
    }

    private fun initObservers() {
        viewModel.isLoadingPage().observe(this, Observer {
            if (it && viewModel.getCurrentPage() == 1) showLoading() else hideLoading()
        })

        viewModel.getRequestErrorType().observe(this, Observer {
            displayError(it)
        })

        // TODO: ver que onda
        viewModel.getMovies().observe(this, Observer {
            movie_feed.adapter?.notifyDataSetChanged()
            if (scheduleAnimation) movie_feed.scheduleLayoutAnimation()
            scheduleAnimation = false
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


    private fun displayError(errorType: ErrorType) {
        when (errorType) {
            ErrorType.SNACKBAR -> {
            }
            ErrorType.FULL_SCREEN -> {
                network_error_text.visibility = View.VISIBLE
            }
        }
    }
}