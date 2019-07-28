package com.example.rappitest.repository.remote

import com.example.rappitest.models.GenresResponse
import com.example.rappitest.models.MoviePageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbService {

    companion object {
        const val BaseUrl = "https://api.themoviedb.org/3/"
        const val PageSize = 20
    }

    object Paths {
        const val Popular = "popular"
        const val TopRated = "top_rated"
        const val Upcoming = "upcoming"
        const val Movie = "movie"
        const val Genre = "genre"
        const val List = "list"
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

    @GET(Paths.Movie + "/" + Paths.TopRated)
    fun getTopRatedMovies(
        @Query(QueryParams.ApiKey) apiKey: String,
        @Query(QueryParams.Language) language: String,
        @Query(QueryParams.Page) page: Int
    ): Call<MoviePageResponse>

    @GET(Paths.Movie + "/" + Paths.Upcoming)
    fun getUpcomingMovies(
        @Query(QueryParams.ApiKey) apiKey: String,
        @Query(QueryParams.Language) language: String,
        @Query(QueryParams.Page) page: Int
    ): Call<MoviePageResponse>

    @GET(Paths.Genre + "/" + Paths.Movie + "/" + Paths.List)
    fun getMovieGenres(
        @Query(QueryParams.ApiKey) apiKey: String,
        @Query(QueryParams.Language) language: String
    ): Call<GenresResponse>
}