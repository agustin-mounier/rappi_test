package com.example.rappitest.repository

import androidx.lifecycle.LiveData
import com.example.rappitest.models.Movie
import com.example.rappitest.repository.remote.RequestAction

interface TmdbRepositoryApi {

    fun isLoading(): LiveData<Boolean>

    fun getRequestErrorAction(): LiveData<RequestAction>

    fun getMovies(): LiveData<List<Movie>>

    fun getMovieGenres(): Map<Int, String>

    fun fetchPopularMovies(page: Int)

    fun fetchTopRatedMovies(page: Int)

    fun fetchUpcomingMovies(page: Int)

    fun clearMovies()
}