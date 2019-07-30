package com.example.rappitest.viewmodels

import androidx.lifecycle.ViewModel
import com.example.rappitest.models.Movie
import com.example.rappitest.repository.TmdbRepositoryApi
import javax.inject.Inject

class TmdbDetailViewModel @Inject constructor(private val repository: TmdbRepositoryApi): ViewModel() {

    fun getMovie(movieId: Int): Movie? {
        return repository.getMovie(movieId)
    }
}