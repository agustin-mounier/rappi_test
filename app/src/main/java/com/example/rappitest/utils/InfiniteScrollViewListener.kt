package com.example.rappitest.utils

import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rappitest.models.Movie

class InfiniteScrollViewListener(
    private val category: Movie.Category,
    private val mLayoutManager: LinearLayoutManager,
    private val isLoadingPage: LiveData<Boolean>,
    private var loadData: (Movie.Category) -> Unit
) : RecyclerView.OnScrollListener() {

    private var visibleThreshold = 5
    private var previousTotalItemCount = 0
    private var loading = true
    private var enabled = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (!enabled) {
            return
        }

        val totalItemCount = mLayoutManager.itemCount
        val lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()

        // List was invalidated. Reset state.
        if (totalItemCount < previousTotalItemCount) {
            this.previousTotalItemCount = totalItemCount
        }
        // Finished loading new items.
        if (isLoadingPage.value!! && totalItemCount > previousTotalItemCount) {
            previousTotalItemCount = totalItemCount
        }

        // Time to load more items. The threshold was breached.
        if (!isLoadingPage.value!! && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            loadData(category)
        }
    }

    fun resetState() {
        this.previousTotalItemCount = 0
        this.loading = true
    }

    fun setEnable(bool: Boolean) {
        this.enabled = bool
    }

    fun setLoading(bool: Boolean) {
        this.loading = bool
    }
}