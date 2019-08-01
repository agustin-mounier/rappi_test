package com.example.rappitest.repository.remote

import androidx.lifecycle.LiveData
import com.example.rappitest.models.GenresResponse
import com.example.rappitest.models.MoviePageResponse
import com.example.rappitest.models.VideosResponse

interface TmdbServiceApi {

    fun isLoading(): LiveData<Boolean>

    fun isLoadingPage(): LiveData<Boolean>

    fun getRequestErrorAction(): LiveData<ErrorType>

    fun getPopularMovies(page: Int, onSuccessFun: (MoviePageResponse?) -> Unit)

    fun getTopRatedMovies(page: Int, onSuccessFun: (MoviePageResponse?) -> Unit)

    fun getUpcomingMovies(page: Int, onSuccessFun: (MoviePageResponse?) -> Unit)

    fun getMovieGenres(): GenresResponse?

    fun getMovieVideos(movieId: Int, onSuccessFun: (VideosResponse?) -> Unit)
}