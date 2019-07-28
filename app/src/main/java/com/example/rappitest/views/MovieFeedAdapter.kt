package com.example.rappitest.views

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.rappitest.R
import com.example.rappitest.models.Movie

class MovieFeedAdapter(
    private val movies: LiveData<List<Movie>>,
    private val genresMap: Map<Int, String>
) : RecyclerView.Adapter<MovieViewHolder>(), Filterable {

    private var filteredMovies: List<Movie>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.movie_item_view, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        filteredMovies?.let {
            return it.size
        }
        return movies.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (filteredMovies == null) {
            holder.bind(movies.value!![position], genresMap)
        } else {
            holder.bind(filteredMovies!![position], genresMap)
        }
    }

    fun setFilteredMovies(movies: List<Movie>) {
        filteredMovies = movies
    }

    override fun getFilter(): Filter {
        return TmdbFilter(movies.value!!, this)
    }
}