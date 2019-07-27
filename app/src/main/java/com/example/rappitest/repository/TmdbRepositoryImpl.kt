package com.example.rappitest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rappitest.models.Movie
import com.example.rappitest.repository.remote.RequestAction
import com.example.rappitest.repository.remote.TmdbServiceApi
import javax.inject.Inject

class TmdbRepositoryImpl @Inject constructor(private val tmdbService: TmdbServiceApi) : TmdbRepositoryApi {


    override fun isLoading(): LiveData<Boolean> {
        return tmdbService.isLoading()
    }

    override fun getRequestErrorAction(): LiveData<RequestAction> {
        return tmdbService.getRequestErrorAction()
    }

    override fun getPopualarMovies(page: Int): LiveData<List<Movie>> {
        return MutableLiveData()
    }
}