package com.example.rappitest.repository.local

import com.example.rappitest.models.Movie
import com.example.rappitest.models.MovieCategories
import com.example.rappitest.models.MovieGenres
import com.example.rappitest.models.MovieGenresEntry
import io.realm.Realm
import io.realm.RealmList
import javax.inject.Inject

class TmdbDaoImpl @Inject constructor(private val realm: Realm) : TmdbDao {

    override fun closeRealm() {
        realm.close()
    }

    override fun persistMovies(movies: List<Movie>?) {
        realm.executeTransactionAsync { it.insertOrUpdate(movies) }
    }

    override fun retrieveMoviesWithIds(ids: List<Int>): List<Movie> {
        return realm.where(Movie::class.java).`in`("id", ids.toTypedArray()).findAll()
    }

    override fun updateMovieCategories(movies: List<Movie>, category: Movie.Category) {
        val movieCategories = retrieveMovieCategories()
        realm.beginTransaction()
        when (category) {
            Movie.Category.Popular -> {
                movieCategories.popularIds!!.addAll(movies.map { it.id })
            }
            Movie.Category.TopRated -> {
                movieCategories.topRatedIds!!.addAll(movies.map { it.id })
            }
            Movie.Category.Upcoming -> {
                movieCategories.upcomingIds!!.addAll(movies.map { it.id })
            }
        }
        realm.commitTransaction()
    }

    override fun retrieveMovieCategories(): MovieCategories {
        val movieCategories = realm.where(MovieCategories::class.java).findFirst()
        if (movieCategories == null) {
            realm.beginTransaction()
            val newMovieCategories = realm.createObject(MovieCategories::class.java)
            realm.commitTransaction()
            return newMovieCategories
        }
        return movieCategories
    }

    override fun persistMovieGenres(movieGenres: Map<Int, String>) {
        realm.executeTransaction {
            val realmList = movieGenres.entries.mapTo(RealmList()) { entry -> MovieGenresEntry(entry.key, entry.value) }
            it.insertOrUpdate(MovieGenres(realmList))
        }
    }

    override fun retrieveMovieGenres(): Map<Int, String> {
        val map = mutableMapOf<Int, String>()
        realm.where(MovieGenres::class.java).findFirst()?.movieGenresEntries?.forEach { map[it.key!!] = it.value!! }
        return map
    }

    override fun retrieveMovie(movieId: Int): Movie? {
        return realm.where(Movie::class.java).equalTo("id", movieId).findFirst()
    }
}