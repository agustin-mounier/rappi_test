package com.example.rappitest.models

import io.realm.RealmList
import io.realm.RealmObject

open class GenresResponse(
    var genres: RealmList<Genre>? = null
) : RealmObject()