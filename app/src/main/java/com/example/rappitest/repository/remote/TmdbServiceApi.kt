package com.example.rappitest.repository.remote

import androidx.lifecycle.LiveData
import com.example.rappitest.models.MoviePageResponse

interface TmdbServiceApi {

    fun isLoading(): LiveData<Boolean>

    fun getRequestErrorAction(): LiveData<RequestAction>

    fun getPopularMovies(page: Int, onSuccessFun: (MoviePageResponse?) -> Unit)
}