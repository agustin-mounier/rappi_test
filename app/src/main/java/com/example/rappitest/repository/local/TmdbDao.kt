package com.example.rappitest.repository.local

import com.example.rappitest.models.Movie
import com.example.rappitest.models.MovieCategories

interface TmdbDao {

    fun closeRealm()

    fun persistMovies(movies: List<Movie>?)

    fun retrieveMoviesWithIds(ids: List<Int>): List<Movie>

    fun updateMovieCategories(movies: List<Movie>, category: Movie.Category)

    fun retrieveMovieCategories(): MovieCategories

    fun persistMovieGenres(movieGenres: Map<Int, String>)

    fun retrieveMovieGenres(): Map<Int, String>

    fun retrieveMovie(movieId: Int): Movie?
}