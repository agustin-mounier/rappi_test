package com.example.rappitest.models

import io.realm.RealmList
import io.realm.RealmObject

open class MovieCategories(
    var popularIds: RealmList<Int> = RealmList(),
    var topRatedIds: RealmList<Int> = RealmList(),
    var upcomingIds: RealmList<Int> = RealmList()
) : RealmObject() {
    fun get(category: Movie.Category): RealmList<Int>? = when(category) {
        Movie.Category.Popular -> popularIds
        Movie.Category.TopRated -> topRatedIds
        Movie.Category.Upcoming -> upcomingIds
    }
}