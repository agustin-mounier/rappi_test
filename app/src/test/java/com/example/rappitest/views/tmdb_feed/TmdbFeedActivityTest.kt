package com.example.rappitest.views.tmdb_feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import com.nhaarman.mockitokotlin2.validateMockitoUsage
import io.realm.Realm
import kotlinx.android.synthetic.main.tmdb_feed_layout.*
import org.junit.*
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.mockito.PowerMockito.`when`
import org.powermock.api.mockito.PowerMockito.mockStatic
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner


@RunWith(PowerMockRunner::class)
@PowerMockRunnerDelegate(RobolectricTestRunner::class)
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest(Realm::class, ViewModelProvider::class)
class TmdbFeedActivityTest {

    /*
    //TODO: Solve MissingLibraryException: librealm-jni.dylib

    lateinit var activity: TmdbFeedActivity

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        mockStatic(Realm::class.java)
        mockStatic(ViewModelProvider::class.java)

        val mockRealm = PowerMockito.mock(Realm::class.java)
        `when`(Realm.getDefaultInstance()).thenReturn(mockRealm)

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