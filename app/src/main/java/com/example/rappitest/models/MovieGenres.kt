package com.example.rappitest.models

import io.realm.RealmList
import io.realm.RealmObject

open class MovieGenres(
    var movieGenresEntries: RealmList<MovieGenresEntry>? = null
) : RealmObject()