package com.example.museumbatikv1 // Ganti dengan package Anda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CollectionAdapter(
    private val collectionList: List<CollectionItem>,
    // 1. Tambahkan parameter untuk listener
    private val onItemClicked: (CollectionItem) -> Unit
) : RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    class CollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val collectionImage: ImageView = itemView.findViewById(R.id.iv_collection_image)
        val collectionName: TextView = itemView.findViewById(R.id.tv_collection_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collection, parent, false)
        return CollectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val currentItem = collectionList[position]
        holder.collectionName.text = currentItem.name

        val imageResId = holder.itemView.context.resources.getIdentifier(
            currentItem.imageName, "drawable", holder.itemView.context.packageName
        )
        holder.collectionImage.setImageResource(imageResId)

        // 2. Atur OnClickListener untuk seluruh item view
        holder.itemView.setOnClickListener {
            onItemClicked(currentItem)
        }
    }

    override fun getItemCount() = collectionList.size
}