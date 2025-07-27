package com.example.museumbatikv1 // Ganti dengan package Anda

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter(private val context: Context, private val galleryList: List<CollectionItem>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val collectionImage: ImageView = itemView.findViewById(R.id.iv_collection_image)
        val collectionName: TextView = itemView.findViewById(R.id.tv_collection_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collection, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val currentItem = galleryList[position]
        holder.collectionName.text = currentItem.name

        val imageResId = holder.itemView.context.resources.getIdentifier(
            currentItem.imageName, "drawable", holder.itemView.context.packageName
        )
        holder.collectionImage.setImageResource(imageResId)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, GalleryDetailActivity::class.java).apply {
                putExtra("EXTRA_NAME", currentItem.name)
                putExtra("EXTRA_IMAGE", currentItem.imageName)
                putExtra("EXTRA_DESC", currentItem.description)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = galleryList.size
}