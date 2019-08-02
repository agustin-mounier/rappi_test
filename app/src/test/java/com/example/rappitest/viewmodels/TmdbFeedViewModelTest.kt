package com.example.rappitest.viewmodels

import androidx.lifecycle.LiveData
import com.example.rappitest.models.Movie
import com.example.rappitest.repository.TmdbRepositoryApi
import com.example.rappitest.repository.remote.ErrorType
import com.nhaarman.mockitokotlin2.validateMockitoUsage
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TmdbFeedViewModelTest {

    @Mock
    lateinit var repository: TmdbRepositoryApi
    @Mock
    lateinit var requestErrorType: LiveData<ErrorType>
    @Mock
    lateinit var isLoading: LiveData<Boolean>
    @Mock
    lateinit var isLoadingPage: LiveData<Boolean>
    private val currentPage = 1

    lateinit var viewModel: TmdbFeedViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(repository.getRequestErrorType()).thenReturn(requestErrorType)
        `when`(repository.isLoading()).thenReturn(isLoading)
        `when`(repository.isLoadingPage()).thenReturn(isLoadingPage)
        `when`(repository.getCurrentPage(Movie.Category.Popular)).thenReturn(currentPage)
        viewModel = TmdbFeedViewModel(repository)
    }

    @After
    fun tearDown() {
        validateMockitoUsage()
    }

    @Test
    fun getMovies() {
        viewModel.getMovies(Movie.Category.Popular)
        verify(repository).getMovies(Movie.Category.Popular)
    }

    @Test
    fun getMovieGenres() {
        viewModel.getMovieGenres()
        verify(repository).getMovieGenres()
    }

    @Test
    fun getRequestErrorType() {
        val errorType = viewModel.getRequestErrorType()
        verify(repository).getRequestErrorType()
        assertEquals(requestErrorType, errorType)
    }

    @Test
    fun fetchMovies() {
        viewModel.fetchMovies(Movie.Category.Popular)
        verify(repository).fetchMovies(Movie.Category.Popular)
    }

    @Test
    fun isLoading() {
        val loading = viewModel.isLoading()
        verify(repository).isLoading()
        assertEquals(isLoading, loading)
    }

    @Test
    fun isLoadingPage() {
        val loading = viewModel.isLoadingPage()
        verify(repository).isLoadingPage()
        assertEquals(isLoadingPage, loading)
    }

    @Test
    fun getCurrentPage() {
        val page = viewModel.getCurrentPage(Movie.Category.Popular)
        verify(repository).getCurrentPage(Movie.Category.Popular)
        assertEquals(currentPage, page)
    }
}