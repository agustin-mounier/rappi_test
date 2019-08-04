package com.example.rappitest.views.tmdb_feed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.rappitest.models.Movie
import org.codehaus.plexus.util.ReflectionUtils
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.api.mockito.PowerMockito



@RunWith(PowerMockRunner::class)
@PrepareForTest(LayoutInflater::class)
class MovieFeedAdapterTest {

    @Mock
    lateinit var movies: LiveData<List<Movie>>
    @Mock
    lateinit var genresMap: LiveData<Map<Int, String>>
    @Mock
    lateinit var isLoadingPage: LiveData<Boolean>
    @Mock
    lateinit var layoutInflater: LayoutInflater

    lateinit var adapter: MovieFeedAdapter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        PowerMockito.mockStatic(LayoutInflater::class.java)
        `when`(layoutInflater.inflate(anyInt(), any(ViewGroup::class.java), anyBoolean())).thenReturn(mock(View::class.java))
        `when`(LayoutInflater.from(any(Context::class.java))).thenReturn(layoutInflater)
        `when`(genresMap.value).thenReturn(mock(Map::class.java) as Map<Int, String>)

        `when`(movies.value).thenReturn(mock(List::class.java) as List<Movie>)
        adapter = MovieFeedAdapter(movies, genresMap, isLoadingPage)
    }

    @After
    fun tearDown() {
        validateMockitoUsage()
    }

    @Test
    fun onCreateViewHolder_MovieViewHolder() {
        val parent = mock(ViewGroup::class.java)
        `when`(parent.context).thenReturn(mock(Context::class.java))

        val viewHolder = adapter.onCreateViewHolder(parent, MovieFeedAdapter.MOVIE_TYPE)

        assertTrue(viewHolder is MovieViewHolder)
    }

    @Test
    fun onCreateViewHolder_LoadingViewHolder() {
        val parent = mock(ViewGroup::class.java)
        `when`(parent.context).thenReturn(mock(Context::class.java))

        val viewHolder = adapter.onCreateViewHolder(parent, MovieFeedAdapter.LOADING_TYPE)

        assertTrue(viewHolder is LoadingViewHolder)
    }

    @Test
    fun getItemCount() {
        val list = listOf(mock(Movie::class.java), mock(Movie::class.java))
        `when`(movies.value).thenReturn(list)

        val itemCount = adapter.itemCount

        assertEquals(list.size, itemCount)
    }

    @Test
    fun getItemCount_withFilteredMovies() {
        val list = listOf(mock(Movie::class.java), mock(Movie::class.java))
        adapter.setFilteredMovies(list)

        val itemCount = adapter.itemCount

        assertEquals(list.size, itemCount)
    }

    @Test
    fun onBindViewHolder() {
        val position = 0
        val list = listOf(mock(Movie::class.java), mock(Movie::class.java))
        val viewHolder = mock(BaseViewHolder::class.java)
        `when`(movies.value).thenReturn(list)

        adapter.onBindViewHolder(viewHolder, position)

        verify(viewHolder).bind(list[position])
    }

    @Test
    fun onBindViewHolder_withFilteredMovies() {
        val position = 0
        val viewHolder = mock(BaseViewHolder::class.java)
        val list = listOf(mock(Movie::class.java), mock(Movie::class.java))
        adapter.setFilteredMovies(list)

        adapter.onBindViewHolder(viewHolder, position)

        verify(viewHolder).bind(list[position])
    }

    @Test
    fun getItemViewType_MovieType() {
        val position = 0
        assertEquals(MovieFeedAdapter.MOVIE_TYPE, adapter.getItemViewType(position))

        val list = listOf(mock(Movie::class.java), mock(Movie::class.java))
        `when`(movies.value).thenReturn(list)
        `when`(isLoadingPage.value).thenReturn(true)

        assertEquals(MovieFeedAdapter.MOVIE_TYPE, adapter.getItemViewType(position))
    }

    @Test
    fun getItemViewType_LoadingType() {
        val position = 1
        val list = listOf(mock(Movie::class.java), mock(Movie::class.java))
        `when`(movies.value).thenReturn(list)
        `when`(isLoadingPage.value).thenReturn(true)

        assertEquals(MovieFeedAdapter.LOADING_TYPE, adapter.getItemViewType(position))
    }

    @Test
    fun setFilteredMovies() {
        val list = listOf(mock(Movie::class.java), mock(Movie::class.java))

        adapter.setFilteredMovies(list)

        val filteredMovies = ReflectionUtils.getValueIncludingSuperclasses("filteredMovies", adapter) as List<Movie>
        assertEquals(list, filteredMovies)
    }

    @Test
    fun getFilter() {
        assertTrue(adapter.filter is TmdbFilter)
    }
}