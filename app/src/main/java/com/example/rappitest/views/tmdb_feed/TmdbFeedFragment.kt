package com.example.rappitest.views.tmdb_feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rappitest.R
import com.example.rappitest.models.Movie
import com.example.rappitest.utils.InfiniteScrollViewListener
import com.example.rappitest.viewmodels.TmdbFeedViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.movie_feed_page_layout.*
import javax.inject.Inject


class TmdbFeedFragment : Fragment() {

    companion object {
        private const val CategoryKey = "category_key"

        fun newInstance(category: Movie.Category): Fragment {
            val fragment = TmdbFeedFragment()
            val args = Bundle()
            args.putSerializable(CategoryKey, category)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: TmdbFeedViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[TmdbFeedViewModel::class.java]
    }

    private lateinit var scrollListener: InfiniteScrollViewListener
    private lateinit var category: Movie.Category

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        AndroidSupportInjection.inject(this)
        category = arguments!![CategoryKey] as Movie.Category
        return inflater.inflate(R.layout.movie_feed_page_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(view.context)
        val adapter =
            MovieFeedAdapter(viewModel.getMovies(category), viewModel.getMovieGenres(), viewModel.isLoadingPage())

        scrollListener = InfiniteScrollViewListener(category, layoutManager, viewModel::fetchMovies)
        movie_feed.layoutManager = layoutManager
        movie_feed.addOnScrollListener(scrollListener)
        movie_feed.adapter = adapter
        initObservers(adapter)
    }

    private fun initObservers(adapter: MovieFeedAdapter) {
        viewModel.getMovieGenres().observe(this, Observer {
            if (!it.isNullOrEmpty()) viewModel.fetchMovies(category)
        })

        viewModel.getMovies(category).observe(this, Observer {
            adapter.notifyDataSetChanged()
            if (viewModel.getCurrentPage(category) == 1) movie_feed.scheduleLayoutAnimation()
        })

        viewModel.isLoadingPage().observe(this, Observer {
            if (it && viewModel.getCurrentPage(category) == 1) showLoading() else hideLoading()
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