package com.example.museumbatikv1 // Ganti dengan package Anda

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.*

class ShopAdapter(private val context: Context, private val shopList: List<ShopItem>) :
    RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    class ShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.iv_product_image)
        val productName: TextView = itemView.findViewById(R.id.tv_product_name)
        val productPrice: TextView = itemView.findViewById(R.id.tv_product_price)
        val buyButton: Button = itemView.findViewById(R.id.btn_buy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shop, parent, false)
        return ShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val currentItem = shopList[position]
        holder.productName.text = currentItem.name

        // Format harga ke format Rupiah
        val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        holder.productPrice.text = format.format(currentItem.price)

        val imageResId = context.resources.getIdentifier(
            currentItem.imageName, "drawable", context.packageName
        )
        holder.productImage.setImageResource(imageResId)

        // Aksi saat tombol "Beli" diklik
        holder.buyButton.setOnClickListener {
            // Contoh: Membuka WhatsApp untuk pemesanan
            val message = "Halo, saya tertarik untuk membeli produk: ${currentItem.name}"
            val phoneNumber = "+6281362555253" // Ganti dengan nomor WhatsApp toko

            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}")
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "Aplikasi WhatsApp tidak terinstall.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount() = shopList.size
}