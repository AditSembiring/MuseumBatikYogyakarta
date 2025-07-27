package com.example.museumbatikv1 // Ganti dengan package Anda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ShopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        supportActionBar?.title = "Toko Suvenir"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val shopList = prepareShopData()
        val shopRecyclerView: RecyclerView = findViewById(R.id.rv_shop)
        shopRecyclerView.adapter = ShopAdapter(this, shopList)
    }

    private fun prepareShopData(): List<ShopItem> {
        // Pastikan Anda memiliki gambar `produk_1`, `produk_2`, dst. di folder drawable
        return listOf(
            ShopItem("Box Tisu Batik", 60000, "produk_1"),
            ShopItem("Asbak Batik", 30000, "produk_2"),
            ShopItem("Yoyo Kayu Batik", 15000, "produk_3"),
            ShopItem("Cermin Saku Batik", 20000, "produk_4"),
            ShopItem("Batik Semesta", 600000, "produk_5"),
            ShopItem("Wahyu Tumurun Latar Ukel 3", 2300000, "produk_6")
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}