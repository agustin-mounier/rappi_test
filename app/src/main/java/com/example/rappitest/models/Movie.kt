package com.example.rappitest.models

import android.content.Context
import com.example.rappitest.repository.remote.TmdbService.Companion.BaseImgUrl
import com.example.rappitest.repository.remote.TmdbService.Companion.PosterResolution
import com.example.rappitest.utils.ResolutionUtils
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Movie(
    @PrimaryKey
    var id: Int = 0,
    var poster_path: String? = null,
    var adult: Boolean = false,
    var overview: String? = null,
    var release_date: String? = null,
    var genre_ids: RealmList<Int>? = null,
    var original_title: String? = null,
    var original_language: String? = null,
    var title: String? = null,
    var backdrop_path: String? = null,
    var popularity: Float = 0.0f,
    var vote_count: Float = 0.0f,
    var video: Boolean = false,
    var vote_average: Float = 0.0f
) : RealmObject() {

    enum class Category {
        Popular, TopRated, Upcoming
    }

    fun getPosterUrl(): String {
        return BaseImgUrl + PosterResolution + poster_path
    }

    fun getBackDropPath(context: Context): String? {
        return backdrop_path?.let{ BaseImgUrl + ResolutionUtils.getBackDropResolution(context) + it }
    }
}