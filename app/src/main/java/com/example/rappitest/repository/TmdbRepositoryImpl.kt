package com.example.rappitest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rappitest.models.Movie
import com.example.rappitest.repository.local.TmdbDao
import com.example.rappitest.repository.remote.RequestAction
import com.example.rappitest.repository.remote.TmdbServiceApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TmdbRepositoryImpl @Inject constructor(
    private val tmdbDao: TmdbDao,
    private val tmdbService: TmdbServiceApi
) : TmdbRepositoryApi {

    private val movies = mutableListOf<Movie>()
    private val liveMovies = MutableLiveData<List<Movie>>()

    override fun isLoading(): LiveData<Boolean> {
        return tmdbService.isLoading()
    }

    override fun getRequestErrorAction(): LiveData<RequestAction> {
        return tmdbService.getRequestErrorAction()
    }

    override fun getPopularMovies(page: Int): LiveData<List<Movie>> {
        tmdbService.getPopularMovies(page) {
            val newMovies = it?.results
            tmdbDao.persistMovies(newMovies)
            newMovies?.let { addMovies(newMovies) }
        }
        return liveMovies
    }

    private fun addMovies(newMovies: List<Movie>) {
        movies.addAll(newMovies)
        liveMovies.postValue(movies)
    }
}