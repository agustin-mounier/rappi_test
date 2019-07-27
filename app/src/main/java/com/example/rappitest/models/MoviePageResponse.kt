package com.example.rappitest.models

data class MoviePageResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<Movie>
)
