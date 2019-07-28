package com.example.rappitest.utils

import com.example.rappitest.repository.local.RealmLiveData
import io.realm.RealmModel
import io.realm.RealmResults

class RealmExtensions {
    fun <T: RealmModel> RealmResults<T>.asLiveData() = RealmLiveData<T>(this)
}