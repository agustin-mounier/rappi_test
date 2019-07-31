package com.example.rappitest.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MovieVideosEntry(
    @PrimaryKey
    var id: Int = 0,
    var videos: RealmList<Video> = RealmList()
) : RealmObject()