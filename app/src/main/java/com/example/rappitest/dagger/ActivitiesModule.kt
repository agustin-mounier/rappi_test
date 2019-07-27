package com.example.rappitest.dagger

import com.example.rappitest.views.TmdbFeedActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    internal abstract fun contributesTmdbFeedActivity(): TmdbFeedActivity
}