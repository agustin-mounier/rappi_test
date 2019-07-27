package com.example.rappitest.repository.local

import com.example.rappitest.models.Movie

interface TmdbDao {

    fun closeRealm()

    fun persistMovies(movies: List<Movie>?)
}