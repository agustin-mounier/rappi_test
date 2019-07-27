package com.example.rappitest.repository.local

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults

class RealmExtensions {
    fun <T: RealmModel> RealmResults<T>.asLiveData() = RealmLiveData<T>(this)
}