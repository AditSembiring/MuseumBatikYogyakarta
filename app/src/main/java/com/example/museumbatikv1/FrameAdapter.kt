package com.example.museumbatikv1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class FrameAdapter(
    private val frameList: List<Int>,
    private val onItemClicked: (Int) -> Unit
) : RecyclerView.Adapter<FrameAdapter.FrameViewHolder>() {

    class FrameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById(R.id.iv_frame_thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_frame, parent, false)
        return FrameViewHolder(view)
    }

    override fun onBindViewHolder(holder: FrameViewHolder, position: Int) {
        val frameResId = frameList[position]

        if (frameResId == 0) {
            // Anda bisa membuat drawable khusus untuk 'tanpa bingkai'
            holder.thumbnail.setImageResource(R.drawable.no_frame_thumbnail)
        } else {
            holder.thumbnail.setImageResource(frameResId)
        }

        holder.itemView.setOnClickListener {
            onItemClicked(frameResId)
        }
    }

    override fun getItemCount() = frameList.size
}