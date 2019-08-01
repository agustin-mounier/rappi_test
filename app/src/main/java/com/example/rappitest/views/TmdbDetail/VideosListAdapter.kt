package com.example.rappitest.views.TmdbDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.rappitest.R
import com.example.rappitest.models.Video

class VideosListAdapter(private val videosList: LiveData<List<Video>>) : RecyclerView.Adapter<VideoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.video_list_item, parent, false)
        return VideoItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videosList.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: VideoItemViewHolder, position: Int) {
        holder.bind(videosList.value!![position])
    }
}