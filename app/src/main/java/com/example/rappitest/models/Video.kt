package com.example.rappitest.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Video : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var key: String? = null
    var name: String? = null
    var site: String? = null
    var size: Int = 0
    var type: String? = null
}