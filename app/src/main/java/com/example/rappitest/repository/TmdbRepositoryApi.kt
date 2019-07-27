package com.example.rappitest.repository

import androidx.lifecycle.LiveData
import com.example.rappitest.models.Movie
import com.example.rappitest.repository.remote.RequestAction

interface TmdbRepositoryApi {

    fun isLoading(): LiveData<Boolean>

    fun getRequestErrorAction(): LiveData<RequestAction>

    fun getPopularMovies(page: Int): LiveData<List<Movie>>
}