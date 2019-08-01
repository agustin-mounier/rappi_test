package com.example.rappitest.repository

import androidx.lifecycle.LiveData
import com.example.rappitest.models.Movie
import com.example.rappitest.models.Video
import com.example.rappitest.repository.remote.ErrorType

interface TmdbRepositoryApi {

    fun isLoading(): LiveData<Boolean>

    fun isLoadingPage(): LiveData<Boolean>

    fun getRequestErrorType(): LiveData<ErrorType>

    fun getMovie(movieId: Int): Movie?

    fun getMovies(category: Movie.Category): LiveData<List<Movie>>

    fun getMovieVideos(movieId: Int): LiveData<List<Video>>

    fun getMovieGenres(): Map<Int, String>

    fun fetchMovies(category: Movie.Category)

    fun fetchMovieVideos(movieId: Int)

    fun getCurrentPage(category: Movie.Category): Int
}