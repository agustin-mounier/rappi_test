package com.example.rappitest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rappitest.models.Movie
import com.example.rappitest.models.Video
import com.example.rappitest.repository.TmdbRepositoryApi
import javax.inject.Inject

class TmdbDetailViewModel @Inject constructor(private val repository: TmdbRepositoryApi): ViewModel() {

    fun getMovie(movieId: Int): Movie? {
        return repository.getMovie(movieId)
    }

    fun getMovieVideos(movieId: Int): LiveData<List<Video>> {
        return repository.getMovieVideos(movieId)
    }

    fun fetchMovieVideos(movieId: Int) {
        repository.fetchMovieVideos(movieId)
    }
}