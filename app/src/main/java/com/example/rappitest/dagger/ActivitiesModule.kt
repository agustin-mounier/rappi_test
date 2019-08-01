package com.example.rappitest.dagger

import com.example.rappitest.views.tmdb_detail.TmdbDetailActivity
import com.example.rappitest.views.tmdb_feed.TmdbFeedActivity
import com.example.rappitest.views.tmdb_feed.TmdbFeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    internal abstract fun contributesTmdbFeedActivity(): TmdbFeedActivity

    @ContributesAndroidInjector
    internal abstract fun contributesTmdbDetailActivity(): TmdbDetailActivity

    @ContributesAndroidInjector
    internal abstract fun contributesTmdbFeedFragment(): TmdbFeedFragment
}