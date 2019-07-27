package com.example.rappitest.repository.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rappitest.BuildConfig
import com.example.rappitest.models.MoviePageResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@Singleton
class TmdbServiceImpl @Inject constructor() : TmdbServiceApi {

    private val tmdbService: TmdbService
    private val lang = Locale.getDefault().language
    private val isLoading = MutableLiveData(false)
    private val requestErrorAction = MutableLiveData(RequestAction.NONE)

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            ).build()

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

    override fun getRequestErrorAction(): LiveData<RequestAction> {
        return requestErrorAction
    }

    override fun getPopularMovies(page: Int, onSuccessFun: (MoviePageResponse?) -> Unit) {
        val callback = TmdbCallback<MoviePageResponse>(isLoading, requestErrorAction, RequestAction.GET_MOST_POPULAR, onSuccessFun)
        val popularMoviesCall = tmdbService.getPopularMovies(BuildConfig.TmdbApiKey, lang, page)
        popularMoviesCall.enqueue(callback)
    }

}