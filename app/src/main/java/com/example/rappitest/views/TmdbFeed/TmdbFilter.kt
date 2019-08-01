package com.example.rappitest.views.TmdbFeed

import android.widget.Filter
import com.example.rappitest.models.Movie

class TmdbFilter(private val movies: List<Movie>, private val adapter: MovieFeedAdapter) : Filter() {
    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val results = FilterResults()

        if (constraint.isNullOrEmpty()) {
            results.count = movies.size
            results.values = movies
        } else {
            val filteredMovies = mutableListOf<Movie>()

            movies.forEach {
                if (it.title?.contains(constraint, true)!!)
                    filteredMovies.add(it)
            }
            results.count = filteredMovies.size
            results.values = filteredMovies
        }

        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        adapter.setFilteredMovies(results?.values as List<Movie>)
        adapter.notifyDataSetChanged()
    }
}