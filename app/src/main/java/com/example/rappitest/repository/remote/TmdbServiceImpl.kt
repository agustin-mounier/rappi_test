package com.example.rappitest.repository.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rappitest.BuildConfig
import com.example.rappitest.models.GenresResponse
import com.example.rappitest.models.MoviePageResponse
import com.example.rappitest.models.VideosResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TmdbServiceImpl @Inject constructor() : TmdbServiceApi {

    private val tmdbService: TmdbService
    private val lang = Locale.getDefault().language
    private val isLoading = MutableLiveData(false)
    private val isLoadingPage = MutableLiveData(false)
    private val requestErrorAction = MutableLiveData(ErrorType.NONE)
    private val timeOut: Long = 10


    init {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        tmdbService = Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(TmdbService.BaseUrl)
            .build()
            .create(TmdbService::class.java)
    }

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
            TmdbCallback<MoviePageResponse>(
                isLoadingPage,
                requestErrorAction,
                ErrorType.FULL_SCREEN,
                onSuccessFun
            )
        val popularMoviesCall = tmdbService.getPopularMovies(BuildConfig.TmdbApiKey, lang, page)
        popularMoviesCall.enqueue(callback)
    }

    override fun getTopRatedMovies(page: Int, onSuccessFun: (MoviePageResponse?) -> Unit) {
        val callback =
            TmdbCallback<MoviePageResponse>(
                isLoadingPage,
                requestErrorAction,
                ErrorType.FULL_SCREEN,
                onSuccessFun
            )
        val popularMoviesCall = tmdbService.getTopRatedMovies(BuildConfig.TmdbApiKey, lang, page)
        popularMoviesCall.enqueue(callback)
    }

    override fun getUpcomingMovies(page: Int, onSuccessFun: (MoviePageResponse?) -> Unit) {
        val callback =
            TmdbCallback<MoviePageResponse>(
                isLoadingPage,
                requestErrorAction,
                ErrorType.FULL_SCREEN,
                onSuccessFun
            )
        val popularMoviesCall = tmdbService.getUpcomingMovies(BuildConfig.TmdbApiKey, lang, page)
        popularMoviesCall.enqueue(callback)
    }

    override fun getMovieGenres(): GenresResponse? {
        val movieGenresCall = tmdbService.getMovieGenres(BuildConfig.TmdbApiKey, lang)
        return movieGenresCall.execute().body()
    }

    override fun getMovieVideos(movieId: Int, onSuccessFun: (VideosResponse?) -> Unit) {
        val callback =
            TmdbCallback<VideosResponse>(
                MutableLiveData(),
                requestErrorAction,
                ErrorType.FULL_SCREEN,
                onSuccessFun
            )
        val movieVideosCall = tmdbService.getMovieVideos(movieId, BuildConfig.TmdbApiKey)
        movieVideosCall.enqueue(callback)
    }
}