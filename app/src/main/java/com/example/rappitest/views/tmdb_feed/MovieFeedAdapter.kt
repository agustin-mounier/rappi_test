package com.example.rappitest.views.tmdb_feed

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
    private val genresMap: Map<Int, String>,
    private val isLoadingPage: LiveData<Boolean>
) : RecyclerView.Adapter<BaseViewHolder>(), Filterable {

    companion object {
        const val MOVIE_TYPE = 0
        const val LOADING_TYPE = 1
    }

    private var filteredMovies: List<Movie>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MOVIE_TYPE -> {
                val view = layoutInflater.inflate(R.layout.movie_item_view, parent, false)
                MovieViewHolder(view, genresMap)
            }
            LOADING_TYPE -> {
                val view = layoutInflater.inflate(R.layout.loading_item_view, parent, false)
                LoadingViewHolder(view)
            }
            else -> null as BaseViewHolder
        }
    }

    override fun getItemCount(): Int {
        filteredMovies?.let {
            return it.size
        }
        return movies.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (filteredMovies == null) {
            holder.bind(movies.value!![position])
        } else {
            holder.bind(filteredMovies!![position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isLoadingPage.value == true) {
            if (position == movies.value!!.size - 1)
                return LOADING_TYPE
            else
                return MOVIE_TYPE
        } else {
            return MOVIE_TYPE
        }
    }

    fun setFilteredMovies(movies: List<Movie>) {
        filteredMovies = movies
    }

    override fun getFilter(): Filter {
        return TmdbFilter(movies.value!!, this)
    }
}