package com.example.rappitest.views.tmdb_feed

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.rappitest.R
import com.example.rappitest.repository.remote.ErrorType
import com.example.rappitest.viewmodels.TmdbFeedViewModel
import com.google.android.material.tabs.TabLayout
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.movie_feed_page_layout.*
import kotlinx.android.synthetic.main.tmdb_feed_layout.*
import javax.inject.Inject


class TmdbFeedActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: TmdbFeedViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[TmdbFeedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tmdb_feed_layout)
        setSupportActionBar(toolbar)
        initView()
        initObservers()
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
                val fragment = supportFragmentManager.fragments[movie_feed_pager.currentItem]
                (fragment.movie_feed.adapter as MovieFeedAdapter).filter.filter(newText)
                return false
            }
        })
        return true
    }

    private fun initView() {
        val pagerAdapter = TmdbPagerAdapter(supportFragmentManager)
        movie_feed_pager.adapter = pagerAdapter
        movie_feed_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(movie_tab_layout))
        movie_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                movie_feed_pager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun initObservers() {
        viewModel.getRequestErrorType().observe(this, Observer {
            displayError(it)
        })
    }

    private fun displayError(errorType: ErrorType) {
        when (errorType) {
            ErrorType.SNACKBAR -> {
            }
            ErrorType.FULL_SCREEN -> {
                //network_error_text.visibility = View.VISIBLE
            }
        }
    }
}