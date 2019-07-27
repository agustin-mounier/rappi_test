package com.example.rappitest.dagger

import dagger.Module
import dagger.Provides
import io.realm.Realm

@Module
class RealmModule {
    @Provides
    fun providesRealm(): Realm = Realm.getDefaultInstance()
}