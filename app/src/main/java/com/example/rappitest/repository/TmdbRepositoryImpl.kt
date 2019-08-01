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
    private val pages = mutableMapOf(
        Movie.Category.Popular to 1,
        Movie.Category.TopRated to 1,
        Movie.Category.Upcoming to 1
    )

    private val movies = mapOf(
        Movie.Category.Popular to MutableLiveData(),
        Movie.Category.TopRated to MutableLiveData(),
        Movie.Category.Upcoming to MutableLiveData<MutableList<Movie>>()
    )

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

    override fun getMovies(category: Movie.Category): LiveData<List<Movie>> {
        return movies.getValue(category) as LiveData<List<Movie>>
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

    override fun fetchMovies(category: Movie.Category) {
        when (category) {
            Movie.Category.Popular -> retrieveOrFetchMovies(Movie.Category.Popular, tmdbService::getPopularMovies)
            Movie.Category.TopRated -> retrieveOrFetchMovies(Movie.Category.TopRated, tmdbService::getTopRatedMovies)
            Movie.Category.Upcoming -> retrieveOrFetchMovies(Movie.Category.Upcoming, tmdbService::getUpcomingMovies)
        }
    }

    override fun fetchMovieVideos(movieId: Int) {
        tmdbService.getMovieVideos(movieId) {
            val videos = it?.results!!
            movieVideos[movieId]!!.value = videos
        }
    }

    override fun getCurrentPage(category: Movie.Category): Int {
        return pages[category]!!
    }

    private fun retrieveOrFetchMovies(
        category: Movie.Category,
        serviceCall: (Int, onSuccessFun: (MoviePageResponse?) -> Unit) -> Unit
    ) {
        if (!isNetworkAvailable() && getMovies(category).value.isNullOrEmpty()) { // fetch movies locally
            val movieCategories = tmdbDao.retrieveMovieCategories()
            val ids = movieCategories.get(category)!!
            val movies = tmdbDao.retrieveMoviesWithIds(ids)
            addMovies(category, movies)
        } else { // fetch movies remotely
            serviceCall(pages[category]!!) {
                val newMovies = it?.results!!
                tmdbDao.persistMovies(newMovies)
                tmdbDao.updateMovieCategories(newMovies, category)
                addMovies(category, newMovies)
                pages[category] = pages[category]!! + 1
            }
        }
    }

    private fun addMovies(category: Movie.Category, newMovies: List<Movie>) {
        val movieList = movies.getValue(category)
        if (movieList.value == null) {
            movieList.value = mutableListOf()
        }
        movieList.value!!.addAll(newMovies)
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