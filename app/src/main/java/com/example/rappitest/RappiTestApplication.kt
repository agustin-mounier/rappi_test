package com.example.rappitest

import android.app.Activity
import android.app.Application
import android.util.Log
import com.example.rappitest.dagger.DaggerAppComponent
import com.getkeepsafe.relinker.MissingLibraryException
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import javax.inject.Inject
import io.realm.RealmConfiguration



class RappiTestApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        try {
            Realm.init(this)
            val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
            Realm.setDefaultConfiguration(config)
        } catch (e : MissingLibraryException) {
            Log.d("Realm", "missing librealm-jni.dylib")
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}