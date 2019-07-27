package com.example.rappitest.repository.remote

import com.example.rappitest.models.MoviePageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbService {

    companion object {
        const val BaseUrl = "https://api.themoviedb.org/4"
    }

    object Paths {
        const val Popular = "popular"
        const val Movie = "movie"
    }

    object QueryParams {
        const val ApiKey = "api_key"
        const val Language = "language"
        const val Page = "page"
    }

    @GET(Paths.Movie + "/" + Paths.Popular)
    fun getPopularMovies(
        @Query(QueryParams.ApiKey) apiKey: String,
        @Query(QueryParams.Language) language: String,
        @Query(QueryParams.Page) page: Int
    ): Call<MoviePageResponse>
}