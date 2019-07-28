package com.example.rappitest.repository.local

import com.example.rappitest.models.Movie
import io.realm.Realm
import javax.inject.Inject

class TmdbDaoImpl @Inject constructor(private val realm: Realm) : TmdbDao {

    override fun closeRealm() {
        realm.close()
    }

    override fun persistMovies(movies: List<Movie>?) {
        realm.executeTransactionAsync { realm ->
            movies?.forEach { realm.insertOrUpdate(it) }
        }
    }

}