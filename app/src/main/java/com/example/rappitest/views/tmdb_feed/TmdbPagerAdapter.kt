package com.example.rappitest.views.tmdb_feed

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.rappitest.models.Movie

class TmdbPagerAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pageTypes = arrayListOf(
        Movie.Category.Popular,
        Movie.Category.TopRated,
        Movie.Category.Upcoming
    )

    override fun getItem(position: Int): Fragment {
        return TmdbFeedFragment.newInstance(pageTypes[position])
    }

    override fun getCount(): Int {
        return pageTypes.size
    }
}