package com.example.rappitest.views.tmdb_feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rappitest.BaseRobolectric
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.validateMockitoUsage
import kotlinx.android.synthetic.main.tmdb_feed_layout.*
import org.junit.*
import org.robolectric.Robolectric
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins
import io.realm.Realm
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PrepareForTest
import org.robolectric.RobolectricTestRunner
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.modules.junit4.rule.PowerMockRule
import org.junit.Rule





@PrepareForTest(Realm::class)
class TmdbFeedActivityTest: BaseRobolectric() {

    /*
    //TODO: Solve MissingLibraryException: librealm-jni.dylib

    lateinit var activity: TmdbFeedActivity

    @get:Rule
    val rule = InstantTaskExecutorRule()
    @get:Rule
    var powerMockRule = PowerMockRule()


    @Before
    fun setUp() {
        PowerMockito.mockStatic(Realm::class.java)
        Mockito.doNothing().`when`(Realm.init(any()))

        activity = Robolectric.buildActivity(TmdbFeedActivity::class.java).create().get()
    }

    @After
    fun tearDown() {
        validateMockitoUsage()
    }

    @Test
    fun onCreate() {
        Assert.assertTrue(activity.movie_feed_pager.adapter is TmdbPagerAdapter)
    }

    @Test
    fun onCreateOptionsMenu() {
    }
    */
}