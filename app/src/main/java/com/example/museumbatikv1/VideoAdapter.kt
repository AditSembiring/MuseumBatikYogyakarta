package com.example.museumbatikv1 // Ganti dengan package Anda

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VideoAdapter(private val context: Context, private val videoList: List<VideoItem>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById(R.id.iv_video_thumbnail)
        val title: TextView = itemView.findViewById(R.id.tv_video_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val currentItem = videoList[position]
        holder.title.text = currentItem.title

        val imageResId = context.resources.getIdentifier(
            currentItem.thumbnailName, "drawable", context.packageName
        )
        holder.thumbnail.setImageResource(imageResId)

        holder.itemView.setOnClickListener {
            watchYoutubeVideo(currentItem.youtubeVideoId)
        }
    }

    override fun getItemCount() = videoList.size

    private fun watchYoutubeVideo(id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$id"))
        try {
            context.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(webIntent)
        }
    }
}