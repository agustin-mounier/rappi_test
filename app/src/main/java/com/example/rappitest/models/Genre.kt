package com.example.rappitest.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Genre(
    @PrimaryKey
    var id: Int = 0,
    var name: String? = null
): RealmObject()