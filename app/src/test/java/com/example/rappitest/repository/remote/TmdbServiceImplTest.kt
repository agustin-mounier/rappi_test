package com.example.rappitest.repository.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rappitest.BuildConfig
import com.example.rappitest.models.GenresResponse
import com.example.rappitest.models.MoviePageResponse
import com.example.rappitest.models.VideosResponse
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.validateMockitoUsage
import com.nhaarman.mockitokotlin2.verify
import org.junit.*
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Call
import java.util.*

class TmdbServiceImplTest {

    @Mock
    lateinit var service: TmdbService
    @Mock
    lateinit var moviePageCall: Call<MoviePageResponse>
    @Mock
    lateinit var genresCall: Call<GenresResponse>
    @Mock
    lateinit var videosCall: Call<VideosResponse>

    lateinit var serviceImpl: TmdbServiceImpl
    private val lang = Locale.getDefault().language

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(service.getMovieGenres(anyString(), anyString())).thenReturn(genresCall)
        `when`(service.getPopularMovies(anyString(), anyString(), anyInt())).thenReturn(moviePageCall)
        `when`(service.getTopRatedMovies(anyString(), anyString(), anyInt())).thenReturn(moviePageCall)
        `when`(service.getUpcomingMovies(anyString(), anyString(), anyInt())).thenReturn(moviePageCall)
        `when`(service.getMovieVideos(anyInt(), anyString())).thenReturn(videosCall)

        serviceImpl = TmdbServiceImpl(service)
    }

    @After
    fun tearDown() {
        validateMockitoUsage()
    }

    @Test
    fun isLoading() {
        Assert.assertNotNull(serviceImpl.isLoading())
        Assert.assertFalse(serviceImpl.isLoading().value!!)

        serviceImpl.getMovieGenres {}
        Assert.assertTrue(serviceImpl.isLoading().value!!)
    }

    @Test
    fun isLoadingPage() {
        Assert.assertNotNull(serviceImpl.isLoadingPage())
        Assert.assertFalse(serviceImpl.isLoadingPage().value!!)

        serviceImpl.getPopularMovies(1) {}
        Assert.assertTrue(serviceImpl.isLoadingPage().value!!)
    }

    @Test
    fun getRequestErrorAction() {
        Assert.assertEquals(ErrorType.NONE, serviceImpl.getRequestErrorAction().value)
    }

    @Test
    fun getPopularMovies() {
        val page = 1
        val onSuccessFun: (MoviePageResponse?) -> Unit = {}
        val argCaptor = argumentCaptor<TmdbCallback<MoviePageResponse>>()

        serviceImpl.getPopularMovies(page, onSuccessFun)

        verify(service).getPopularMovies(BuildConfig.TmdbApiKey, lang, page)
        verify(moviePageCall).enqueue(argCaptor.capture())
        Assert.assertEquals(onSuccessFun, argCaptor.firstValue.onSuccessFun)
        Assert.assertEquals(ErrorType.SNACKBAR, argCaptor.firstValue.errorType)
    }

    @Test
    fun getTopRatedMovies() {
        val page = 1
        val onSuccessFun: (MoviePageResponse?) -> Unit = {}
        val argCaptor = argumentCaptor<TmdbCallback<MoviePageResponse>>()

        serviceImpl.getTopRatedMovies(page, onSuccessFun)

        verify(service).getTopRatedMovies(BuildConfig.TmdbApiKey, lang, page)
        verify(moviePageCall).enqueue(argCaptor.capture())
        Assert.assertEquals(onSuccessFun, argCaptor.firstValue.onSuccessFun)
        Assert.assertEquals(ErrorType.SNACKBAR, argCaptor.firstValue.errorType)
    }

    @Test
    fun getUpcomingMovies() {
        val page = 1
        val onSuccessFun: (MoviePageResponse?) -> Unit = {}
        val argCaptor = argumentCaptor<TmdbCallback<MoviePageResponse>>()

        serviceImpl.getUpcomingMovies(page, onSuccessFun)

        verify(service).getUpcomingMovies(BuildConfig.TmdbApiKey, lang, page)
        verify(moviePageCall).enqueue(argCaptor.capture())
        Assert.assertEquals(onSuccessFun, argCaptor.firstValue.onSuccessFun)
        Assert.assertEquals(ErrorType.SNACKBAR, argCaptor.firstValue.errorType)
    }

    @Test
    fun getMovieGenres() {
        val onSuccessFun: (GenresResponse?) -> Unit = {}
        val argCaptor = argumentCaptor<TmdbCallback<GenresResponse>>()

        serviceImpl.getMovieGenres(onSuccessFun)

        verify(service).getMovieGenres(BuildConfig.TmdbApiKey, lang)
        verify(genresCall).enqueue(argCaptor.capture())
        Assert.assertEquals(onSuccessFun, argCaptor.firstValue.onSuccessFun)
        Assert.assertEquals(ErrorType.FULL_SCREEN, argCaptor.firstValue.errorType)
    }

    @Test
    fun getMovieVideos() {
        val movieId = 1
        val onSuccessFun: (VideosResponse?) -> Unit = {}
        val argCaptor = argumentCaptor<TmdbCallback<VideosResponse>>()

        serviceImpl.getMovieVideos(movieId, onSuccessFun)

        verify(service).getMovieVideos(movieId, BuildConfig.TmdbApiKey)
        verify(videosCall).enqueue(argCaptor.capture())
        Assert.assertEquals(onSuccessFun, argCaptor.firstValue.onSuccessFun)
        Assert.assertEquals(ErrorType.SNACKBAR, argCaptor.firstValue.errorType)
    }
}