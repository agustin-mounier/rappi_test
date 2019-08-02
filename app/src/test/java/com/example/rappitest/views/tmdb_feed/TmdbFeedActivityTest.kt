package com.example.rappitest.views.tmdb_feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.validateMockitoUsage
import kotlinx.android.synthetic.main.tmdb_feed_layout.*
import org.junit.*
import org.robolectric.Robolectric

class TmdbFeedActivityTest {

    lateinit var activity: TmdbFeedActivity

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        //activity = Robolectric.buildActivity(TmdbFeedActivity::class.java).create().get()
    }

    @After
    fun tearDown() {
        validateMockitoUsage()
    }

    @Test
    fun onCreate() {
        //Assert.assertTrue(activity.movie_feed_pager.adapter is TmdbPagerAdapter)
    }

    @Test
    fun onCreateOptionsMenu() {
    }
}