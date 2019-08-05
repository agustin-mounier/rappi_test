package com.example.rappitest.views.tmdb_feed

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.example.rappitest.R
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.movie_feed_page_layout.*
import kotlinx.android.synthetic.main.tmdb_feed_layout.*


class TmdbFeedActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tmdb_feed_layout)
        initView()
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

    fun initView() {
        setSupportActionBar(toolbar)
        val pagerAdapter = TmdbPagerAdapter(supportFragmentManager)
        movie_feed_pager.adapter = pagerAdapter
        movie_feed_pager.offscreenPageLimit = 3
        movie_feed_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(movie_tab_layout))
        movie_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                movie_feed_pager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}