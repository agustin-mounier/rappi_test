package com.example.rappitest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rappitest.models.Movie
import com.example.rappitest.repository.TmdbRepositoryApi
import com.example.rappitest.repository.remote.ErrorType
import javax.inject.Inject

class TmdbFeedViewModel @Inject constructor(private val repository: TmdbRepositoryApi) : ViewModel() {


    fun getMovies(category: Movie.Category): LiveData<List<Movie>> {
        return repository.getMovies(category)
    }

    fun getMovieGenres(): Map<Int, String> {
        return repository.getMovieGenres()
    }

    fun getRequestErrorType(): LiveData<ErrorType> {
        return repository.getRequestErrorType()
    }

    fun fetchMovies(category: Movie.Category) {
        repository.fetchMovies(category)
    }

    fun isLoading(): LiveData<Boolean> {
        return repository.isLoading()
    }

    fun isLoadingPage(): LiveData<Boolean> {
        return repository.isLoadingPage()
    }

    fun getCurrentPage(category: Movie.Category): Int {
        return repository.getCurrentPage(category)
    }
}