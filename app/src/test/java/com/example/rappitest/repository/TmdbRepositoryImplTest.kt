package com.example.rappitest.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.rappitest.models.Movie
import com.example.rappitest.models.MovieCategories
import com.example.rappitest.models.MoviePageResponse
import com.example.rappitest.repository.local.TmdbDao
import com.example.rappitest.repository.remote.TmdbServiceApi
import com.nhaarman.mockitokotlin2.argumentCaptor
import io.realm.RealmList
import org.codehaus.plexus.util.ReflectionUtils
import org.junit.*
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class TmdbRepositoryImplTest {

    @Mock
    lateinit var app: Application
    @Mock
    lateinit var dao: TmdbDao
    @Mock
    lateinit var service: TmdbServiceApi

    @Mock
    lateinit var movieGenres: Map<Int, String>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repository: TmdbRepositoryApi

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        `when`(dao.retrieveMovieGenres()).thenReturn(movieGenres)
        repository = TmdbRepositoryImpl(app, dao, service)
    }

    @After
    fun after() {
    }

    @Test
    fun testIsLoading() {
        repository.isLoading()

        verify(service).isLoading()
    }

    @Test
    fun testIsLoadingPage() {
        repository.isLoadingPage()

        verify(service).isLoadingPage()
    }

    @Test
    fun testGetRequestErrorType() {
        repository.getRequestErrorType()

        verify(service).getRequestErrorAction()
    }

    @Test
    fun testGetMovie() {
        val movieId = 1
        repository.getMovie(movieId)

        verify(dao).retrieveMovie(movieId)
    }

    @Test
    fun testGetMovieGenres() {
        val genres = repository.getMovieGenres()

        Assert.assertEquals(movieGenres, genres.value)
    }

    @Test
    fun testGetMovieVideos() {
        val movies = mockMoviesMapIntoRepository()
        val upcoming = repository.getMovies(Movie.Category.Upcoming)
        val topRated = repository.getMovies(Movie.Category.TopRated)
        val popular = repository.getMovies(Movie.Category.Popular)

        Assert.assertEquals(movies[Movie.Category.Popular], popular)
        Assert.assertEquals(movies[Movie.Category.TopRated], topRated)
        Assert.assertEquals(movies[Movie.Category.Upcoming], upcoming)
    }

    @Test
    fun testGetMovieVideosWithoutPreviousValues() {
        Assert.assertNotNull(repository.getMovies(Movie.Category.Popular))
        Assert.assertNotNull(repository.getMovies(Movie.Category.TopRated))
        Assert.assertNotNull(repository.getMovies(Movie.Category.Upcoming))
    }

    @Test
    fun testFetchMovies_WithNetworkConnection() {
        val movies = mockMoviesMapIntoRepository()
        mockNetworkEnable(true)
        val argCaptor = argumentCaptor<(MoviePageResponse?) -> Unit>()

        repository.fetchMovies(Movie.Category.Popular)
        verify(service).getPopularMovies(ArgumentMatchers.anyInt(), argCaptor.capture())

        val movieList = mock(List::class.java) as List<Movie>
        val response = MoviePageResponse(1, 20, 1, movieList)
        argCaptor.firstValue.invoke(response)

        verify(dao).persistMovies(movieList)
        verify(dao).updateMovieCategories(movieList, Movie.Category.Popular)
        verify(movies[Movie.Category.Popular]?.value)!!.addAll(movieList)
    }

    @Test
    fun testFetchMovies_WithoutNetworkConnection() {
        val movies = mockMoviesMapIntoRepository()
        mockNetworkEnable(false)
        val idList = mock(RealmList::class.java) as RealmList<Int>
        val movieCategories = MovieCategories(idList)
        val movieList = mock(List::class.java) as List<Movie>
        `when`(dao.retrieveMovieCategories()).thenReturn(movieCategories)
        `when`(dao.retrieveMoviesWithIds(idList)).thenReturn(movieList)

        repository.fetchMovies(Movie.Category.Popular)

        verify(dao).retrieveMovieCategories()
        verify(dao).retrieveMoviesWithIds(movieCategories.get(Movie.Category.Popular) as List<Int>)
        verify(movies[Movie.Category.Popular]?.value)!!.addAll(movieList)
    }

    /*
    @Test
    fun testFetchMovieVides() {
        val argCaptor = argumentCaptor<(VideosResponse?) -> Unit>()
        val movieId = 1

        repository.fetchMovieVideos(movieId)
        verify(service).getMovieVideos(movieId, argCaptor.capture())

        val videosList = mock(List::class.java) as List<Video>
        val videosResponse = VideosResponse(movieId, videosList)
        argCaptor.firstValue.invoke(videosResponse)

    }
    */

    private fun mockNetworkEnable(enable: Boolean) {
        val conectivityManager = mock(ConnectivityManager::class.java)
        val networkInfo = mock(NetworkInfo::class.java)
        `when`(networkInfo.isConnected).thenReturn(enable)
        `when`(conectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        `when`(app.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(conectivityManager)
    }

    private fun mockMoviesMapIntoRepository(): Map<Movie.Category, MutableLiveData<MutableList<Movie>>> {
        val movies = mapOf<Movie.Category, MutableLiveData<MutableList<Movie>>>(
            Movie.Category.Popular to MutableLiveData(mock(MutableList::class.java) as MutableList<Movie>),
            Movie.Category.TopRated to MutableLiveData(mock(MutableList::class.java) as MutableList<Movie>),
            Movie.Category.Upcoming to MutableLiveData(mock(MutableList::class.java) as MutableList<Movie>)
        )
        ReflectionUtils.setVariableValueInObject(repository, "movies", movies)
        return movies
    }

    @After
    fun validate() {
        validateMockitoUsage()
    }

}