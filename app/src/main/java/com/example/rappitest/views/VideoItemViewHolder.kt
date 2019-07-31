package com.example.rappitest.views

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.rappitest.BuildConfig
import com.example.rappitest.models.Video
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import kotlinx.android.synthetic.main.video_list_item.view.*

class VideoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    YouTubeThumbnailView.OnInitializedListener {

    private var youtubeLoader: YouTubeThumbnailLoader? = null
    private var video: Video? = null

    init {
        itemView.movie_thumbnail.initialize(BuildConfig.YouTubeApiKey, this)
        itemView.setOnClickListener {
            playYoutubeVideo(video!!.key)
        }
    }

    fun bind(video: Video) {
        this.video = video
        youtubeLoader?.let { setThumbnail(video) }
    }

    private fun setThumbnail(video: Video?) {
        youtubeLoader?.setVideo(video?.key)
    }

    private fun playYoutubeVideo(key: String?) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key"))
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$key"))

        try {
            itemView.context.startActivity(appIntent)
        } catch (e: ActivityNotFoundException) {
            itemView.context.startActivity(webIntent)
        }
    }

    override fun onInitializationSuccess(p0: YouTubeThumbnailView?, p1: YouTubeThumbnailLoader?) {
        youtubeLoader = p1
        setThumbnail(video)
    }

    override fun onInitializationFailure(p0: YouTubeThumbnailView?, p1: YouTubeInitializationResult?) {
        Log.e("YOUTUBE_TAG", "Error initializing thumbnail $p1")
    }
}