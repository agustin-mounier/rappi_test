package com.example.rappitest.repository

import android.app.Application
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rappitest.models.Movie
import com.example.rappitest.models.MoviePageResponse
import com.example.rappitest.models.Video
import com.example.rappitest.repository.local.TmdbDao
import com.example.rappitest.repository.remote.ErrorType
import com.example.rappitest.repository.remote.TmdbServiceApi
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TmdbRepositoryImpl @Inject constructor(
    private val app: Application,
    private val tmdbDao: TmdbDao,
    private val tmdbService: TmdbServiceApi
) : TmdbRepositoryApi {
    private val movies = mutableListOf<Movie>()
    private val liveMovies = MutableLiveData<List<Movie>>()
    private var movieGenres: Map<Int, String> = mutableMapOf()
    private var movieVideos = mutableMapOf<Int, MutableLiveData<List<Video>>>()

    init {
        fetchMovieGenres()
    }

    override fun isLoading(): LiveData<Boolean> {
        return tmdbService.isLoading()
    }

    override fun isLoadingPage(): LiveData<Boolean> {
        return tmdbService.isLoadingPage()
    }

    override fun getRequestErrorType(): LiveData<ErrorType> {
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

    override fun getMovieVideos(movieId: Int): LiveData<List<Video>> {
        if (movieVideos[movieId] == null) {
            val liveList = MutableLiveData<List<Video>>()
            movieVideos[movieId] = liveList
            return liveList
        }
        return movieVideos[movieId]!!
    }

    override fun fetchPopularMovies(page: Int) {
        retrieveOrFetchMovies(page, Movie.Category.Popular, tmdbService::getPopularMovies)
    }

    override fun fetchTopRatedMovies(page: Int) {
        retrieveOrFetchMovies(page, Movie.Category.TopRated, tmdbService::getTopRatedMovies)
    }

    override fun fetchUpcomingMovies(page: Int) {
        retrieveOrFetchMovies(page, Movie.Category.Upcoming, tmdbService::getUpcomingMovies)
    }

    override fun fetchMovieVideos(movieId: Int) {
        tmdbService.getMovieVideos(movieId) {
            val videos = it?.results!!
            movieVideos[movieId]!!.value = videos
        }
    }

    override fun clearMovies() {
        movies.clear()
    }

    private fun retrieveOrFetchMovies(
        page: Int,
        movieCategory: Movie.Category,
        serviceCall: (Int, onSuccessFun: (MoviePageResponse?) -> Unit) -> Unit
    ) {
        if (!isNetworkAvailable() && movies.isEmpty()) { // fetch movies locally
            val movieCategories = tmdbDao.retrieveMovieCategories()
            val ids = movieCategories.get(movieCategory)!!
            val idsToFetch = ids.filter { id -> movies.find { movie -> movie.id == id } == null }
            val movies = tmdbDao.retrieveMoviesWithIds(idsToFetch)
            addMovies(movies)
        } else { // fetch movies remotely
            serviceCall(page) {
                val newMovies = it?.results!!
                tmdbDao.persistMovies(newMovies)
                tmdbDao.updateMovieCategories(newMovies, movieCategory)
                addMovies(newMovies)
            }
        }
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