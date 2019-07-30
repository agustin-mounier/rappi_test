package com.example.rappitest.repository

import android.app.Application
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rappitest.models.Movie
import com.example.rappitest.models.MovieCategories
import com.example.rappitest.repository.local.TmdbDao
import com.example.rappitest.repository.remote.RequestAction
import com.example.rappitest.repository.remote.TmdbServiceApi
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TmdbRepositoryImpl @Inject constructor(
    private val app: Application,
    private val tmdbDao: TmdbDao,
    private val tmdbService: TmdbServiceApi
) : TmdbRepositoryApi {
    private var offlineMode = false
    private val movies = mutableListOf<Movie>()
    private val liveMovies = MutableLiveData<List<Movie>>()
    private var movieGenres: Map<Int, String> = mutableMapOf()
    private val movieCategories: MovieCategories

    init {
        movieCategories = tmdbDao.retrieveMovieCategories()
        fetchMovieGenres()
    }

    override fun isLoading(): LiveData<Boolean> {
        return tmdbService.isLoading()
    }

    override fun isLoadingPage(): LiveData<Boolean> {
        return tmdbService.isLoadingPage()
    }

    override fun getRequestErrorAction(): LiveData<RequestAction> {
        return tmdbService.getRequestErrorAction()
    }

    override fun getMovie(movieId: Int): Movie? {
        return tmdbDao.retrieveMovie(movieId)
    }

    override fun getMovies(): LiveData<List<Movie>> {
        return liveMovies
    }

    override fun getMovieGenres(): Map<Int, String> {
        return movieGenres
    }

    override fun fetchPopularMovies(page: Int) {
        if (!isNetworkAvailable() && movies.isEmpty()) {
            val ids = movieCategories.get(Movie.Category.Popular)!!
            val idsToFetch = ids.filter { id -> movies.find { movie -> movie.id == id } == null }
            val movies = tmdbDao.retrieveMoviesWithIds(idsToFetch)
            addMovies(movies)
        } else {
            tmdbService.getPopularMovies(page) {
                val newMovies = it?.results!!
                tmdbDao.persistMovies(newMovies)
                tmdbDao.updateMovieCategories(newMovies, Movie.Category.Popular)
                addMovies(newMovies)
            }
        }
    }

    override fun fetchTopRatedMovies(page: Int) {
        tmdbService.getTopRatedMovies(page) {
            val newMovies = it?.results!!
            tmdbDao.persistMovies(newMovies)
            tmdbDao.updateMovieCategories(newMovies, Movie.Category.TopRated)
            addMovies(newMovies)
        }
    }

    override fun fetchUpcomingMovies(page: Int) {
        tmdbService.getUpcomingMovies(page) {
            val newMovies = it?.results!!
            tmdbDao.persistMovies(newMovies)
            tmdbDao.updateMovieCategories(newMovies, Movie.Category.Upcoming)
            addMovies(newMovies)
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
        val genres = tmdbDao.retrieveMovieGenres()
        if (genres.isEmpty()) {
            val genresResponse = tmdbService.getMovieGenres()
            genresResponse?.let {
                val movieMap = mutableMapOf<Int, String>()
                it.genres?.forEach { genre -> movieMap[genre.id] = genre.name!! }
                movieGenres = movieMap
                tmdbDao.persistMovieGenres(movieGenres)
            }
        } else {
            movieGenres = genres
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}