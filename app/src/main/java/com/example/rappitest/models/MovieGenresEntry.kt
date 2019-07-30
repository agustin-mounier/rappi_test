package com.example.rappitest.models

import io.realm.RealmObject

open class MovieGenresEntry(
    var key: Int? = null,
    var value: String? = null
) : RealmObject()