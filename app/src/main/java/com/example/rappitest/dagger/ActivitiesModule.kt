package com.example.rappitest.dagger

import com.example.rappitest.views.TmdbDetail.TmdbDetailActivity
import com.example.rappitest.views.TmdbFeed.TmdbFeedActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    internal abstract fun contributesTmdbFeedActivity(): TmdbFeedActivity


    @ContributesAndroidInjector
    internal abstract fun contributesTmdbDetailActivity(): TmdbDetailActivity
}