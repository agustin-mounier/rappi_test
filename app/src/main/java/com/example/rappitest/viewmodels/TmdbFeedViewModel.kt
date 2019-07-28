package com.example.rappitest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rappitest.models.Movie
import com.example.rappitest.repository.TmdbRepositoryApi
import javax.inject.Inject

class TmdbFeedViewModel @Inject constructor(private val repository: TmdbRepositoryApi) : ViewModel() {

    private var page = 1
    private lateinit var loadMoreMovies: (Int) -> Unit

    fun getMovies(): LiveData<List<Movie>> {
        return repository.getMovies()
    }

    fun getMovieGenres(): Map<Int, String> {
        return repository.getMovieGenres()
    }

    fun loadMoreMovies() {
        loadMoreMovies(page++)
    }

    fun fetchMostPopular() {
        page = 1
        repository.clearMovies()
        loadMoreMovies = repository::fetchPopularMovies
        loadMoreMovies()
    }

    fun fetchUpcoming() {
        page = 1
        repository.clearMovies()
        loadMoreMovies = repository::fetchUpcomingMovies
        loadMoreMovies()
    }

    fun fetchTopRated() {
        page = 1
        repository.clearMovies()
        loadMoreMovies = repository::fetchTopRatedMovies
        loadMoreMovies()
    }


    fun isLoading(): LiveData<Boolean> {
        return repository.isLoading()
    }
}