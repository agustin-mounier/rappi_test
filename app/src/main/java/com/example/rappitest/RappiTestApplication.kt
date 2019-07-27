package com.example.rappitest

import android.app.Application
import io.realm.Realm

class RappiTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}