package com.example.museumbatikv1 // Ganti dengan package Anda

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar?.title = "Tentang Kami"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Cari TextView alamat berdasarkan ID
        val addressTextView: TextView = findViewById(R.id.tv_address)

        // Buat alamat bisa diklik untuk membuka Google Maps
        addressTextView.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=Museum Batik Yogyakarta")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}