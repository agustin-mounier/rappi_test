package com.example.rappitest.repository.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rappitest.BuildConfig
import com.example.rappitest.models.GenresResponse
import com.example.rappitest.models.MoviePageResponse
import com.example.rappitest.models.VideosResponse
import java.util.*
import javax.inject.Inject

class TmdbServiceImpl @Inject constructor(private val tmdbService: TmdbService) : TmdbServiceApi {

    private val lang = Locale.getDefault().language
    private val isLoading = MutableLiveData(false)
    private val isLoadingPage = MutableLiveData(false)
    private val requestErrorAction = MutableLiveData(ErrorType.NONE)

    override fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    override fun isLoadingPage(): LiveData<Boolean> {
        return isLoadingPage
    }

    override fun getRequestErrorAction(): LiveData<ErrorType> {
        return requestErrorAction
    }

    override fun getPopularMovies(page: Int, onSuccessFun: (MoviePageResponse?) -> Unit) {
        val callback =
            TmdbCallback<MoviePageResponse>(isLoadingPage, requestErrorAction, ErrorType.SNACKBAR, onSuccessFun)
        val popularMoviesCall = tmdbService.getPopularMovies(BuildConfig.TmdbApiKey, lang, page)
        popularMoviesCall.enqueue(callback)
    }

    override fun getTopRatedMovies(page: Int, onSuccessFun: (MoviePageResponse?) -> Unit) {
        val callback =
            TmdbCallback<MoviePageResponse>(isLoadingPage, requestErrorAction, ErrorType.SNACKBAR, onSuccessFun)
        val popularMoviesCall = tmdbService.getTopRatedMovies(BuildConfig.TmdbApiKey, lang, page)
        popularMoviesCall.enqueue(callback)
    }

    override fun getUpcomingMovies(page: Int, onSuccessFun: (MoviePageResponse?) -> Unit) {
        val callback =
            TmdbCallback<MoviePageResponse>(isLoadingPage, requestErrorAction, ErrorType.SNACKBAR, onSuccessFun)
        val popularMoviesCall = tmdbService.getUpcomingMovies(BuildConfig.TmdbApiKey, lang, page)
        popularMoviesCall.enqueue(callback)
    }

    override fun getMovieGenres(onSuccessFun: (GenresResponse?) -> Unit) {
        val callback =
            TmdbCallback<GenresResponse>(isLoading, requestErrorAction, ErrorType.FULL_SCREEN, onSuccessFun)
        val movieGenresCall = tmdbService.getMovieGenres(BuildConfig.TmdbApiKey, lang)
        movieGenresCall.enqueue(callback)
    }

    override fun getMovieVideos(movieId: Int, onSuccessFun: (VideosResponse?) -> Unit) {
        val callback =
            TmdbCallback<VideosResponse>(MutableLiveData(), requestErrorAction, ErrorType.SNACKBAR, onSuccessFun)
        val movieVideosCall = tmdbService.getMovieVideos(movieId, BuildConfig.TmdbApiKey)
        movieVideosCall.enqueue(callback)
    }
}