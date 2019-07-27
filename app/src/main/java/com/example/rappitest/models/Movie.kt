package com.example.rappitest.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Movie (
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
) : RealmObject()