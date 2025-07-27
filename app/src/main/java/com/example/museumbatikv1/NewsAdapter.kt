package com.example.museumbatikv1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
// import com.bumptech.glide.Glide // Nanti bisa dipakai untuk memuat gambar dari URL

class NewsAdapter(private val newsList: List<News>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsImage: ImageView = itemView.findViewById(R.id.iv_news_image)
        val newsTitle: TextView = itemView.findViewById(R.id.tv_news_title)
        val newsDate: TextView = itemView.findViewById(R.id.tv_news_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.newsTitle.text = currentItem.title
        holder.newsDate.text = currentItem.date

        // Untuk sementara, kita gunakan placeholder. Nanti bisa di-load dari URL.
        val imageResId = holder.itemView.context.resources.getIdentifier(
            currentItem.imageUrl, "drawable", holder.itemView.context.packageName
        )
        holder.newsImage.setImageResource(imageResId)
    }

    override fun getItemCount() = newsList.size
}