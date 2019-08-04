package com.example.rappitest.views.tmdb_feed

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.rappitest.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.tmdb_feed_layout.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class TmdbFeedActivityTest {

    @Mock
    lateinit var movieFeedPager: ViewPager
    @Mock
    lateinit var movieTabLayout: TabLayout
    @Mock
    lateinit var fragmentManager: FragmentManager
    @Mock
    lateinit var menuInflater: MenuInflater

    lateinit var activity: TmdbFeedActivity

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        activity = spy(Robolectric.buildActivity(TmdbFeedActivity::class.java).get())
        `when`(activity.movie_feed_pager).thenReturn(movieFeedPager)
        `when`(activity.movie_tab_layout).thenReturn(movieTabLayout)
        `when`(activity.supportFragmentManager).thenReturn(fragmentManager)
        `when`(activity.menuInflater).thenReturn(menuInflater)
    }

    @After
    fun tearDown() {
        validateMockitoUsage()
    }

    @Test
    fun initView() {
        val offscreenPageLimit = 3
        activity.initView()

        verify(movieFeedPager).addOnPageChangeListener(any(TabLayout.TabLayoutOnPageChangeListener::class.java))
        verify(movieFeedPager).offscreenPageLimit = offscreenPageLimit
        verify(movieFeedPager).adapter = any(TmdbPagerAdapter::class.java)
        verify(movieTabLayout).addOnTabSelectedListener(any(TabLayout.OnTabSelectedListener::class.java))
    }

    @Test
    fun onCreateOptionsMenu() {
        val searchView = mock(SearchView::class.java)
        val searchItem = mock(MenuItem::class.java)
        val menu = mock(Menu::class.java)
        `when`(searchItem.actionView).thenReturn(searchView)
        `when`(menu.findItem(R.id.action_search)).thenReturn(searchItem)

        activity.onCreateOptionsMenu(menu)

        verify(searchView).setOnQueryTextListener(any(SearchView.OnQueryTextListener::class.java))
    }
}