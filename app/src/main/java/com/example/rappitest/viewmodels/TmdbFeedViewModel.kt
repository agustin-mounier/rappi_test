package com.example.rappitest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rappitest.models.Movie
import com.example.rappitest.repository.TmdbRepositoryApi
import javax.inject.Inject

class TmdbFeedViewModel @Inject constructor(private val repository: TmdbRepositoryApi) : ViewModel() {

    fun fetchMostPopular() : LiveData<List<Movie>> {
        return repository.getPopularMovies(1)
    }
}