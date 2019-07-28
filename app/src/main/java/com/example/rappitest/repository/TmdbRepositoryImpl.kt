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
    private val movieGenres = mutableMapOf<Int, String>()

    init {
        fetchMovieGenres()
    }

    override fun isLoading(): LiveData<Boolean> {
        return tmdbService.isLoading()
    }

    override fun getRequestErrorAction(): LiveData<RequestAction> {
        return tmdbService.getRequestErrorAction()
    }

    override fun getMovies(): LiveData<List<Movie>> {
        return liveMovies
    }

    override fun getMovieGenres(): Map<Int, String> {
        return movieGenres
    }

    override fun fetchPopularMovies(page: Int) {
        tmdbService.getPopularMovies(page) {
            val newMovies = it?.results
            tmdbDao.persistMovies(newMovies)
            newMovies?.let { addMovies(newMovies) }
        }
    }

    override fun fetchTopRatedMovies(page: Int) {
        tmdbService.getTopRatedMovies(page) {
            val newMovies = it?.results
            tmdbDao.persistMovies(newMovies)
            newMovies?.let { addMovies(newMovies) }
        }
    }

    override fun fetchUpcomingMovies(page: Int) {
        tmdbService.getUpcomingMovies(page) {
            val newMovies = it?.results
            tmdbDao.persistMovies(newMovies)
            newMovies?.let { addMovies(newMovies) }
        }
    }

    override fun clearMovies() {
        movies.clear()
    }

    private fun addMovies(newMovies: List<Movie>) {
        movies.addAll(newMovies)
        liveMovies.postValue(movies)
    }

    private fun fetchMovieGenres() {
        tmdbService.getMovieGenres {
            it?.genres?.forEach { genre -> movieGenres[genre.id] = genre.name!! }
        }
    }
}