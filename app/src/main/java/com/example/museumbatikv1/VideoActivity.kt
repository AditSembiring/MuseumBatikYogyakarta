package com.example.museumbatikv1 // Ganti dengan package Anda

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        supportActionBar?.title = "Video Edukasi"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupFeaturedVideo()
        setupVideoCategories()
    }

    private fun setupFeaturedVideo() {
        val featuredVideoCard: CardView = findViewById(R.id.featured_video_card)
        featuredVideoCard.setOnClickListener {
            // Ganti "YOUTUBE_ID_FEATURED" dengan ID video YouTube Anda
            watchYoutubeVideo("0YL2ONQ2cZg")
        }
    }

    private fun setupVideoCategories() {
        // Data untuk kategori 1
        val prosesList = listOf(
            VideoItem("Intip proses pembuatan batik tulis", "7jdyQ4EQ70U", "video_1"),
            VideoItem("Filosofi Motif Batik pada Museum Batik Yogyakarta", "qhEcS8KA9ec", "video_2"),
            VideoItem("Proses QC & finishing batik tulis", "pnSf9Uuo2qg", "video_3")
        )
        val rvProses: RecyclerView = findViewById(R.id.rv_proses_batik)
        rvProses.adapter = VideoAdapter(this, prosesList)

        // Data untuk kategori 2
        val sejarahList = listOf(
            VideoItem("Filosofi Motif Parang", "rD5adsAvGf0", "video_4"),
            VideoItem("Perjalanan Batik di Keraton Yogyakarta-Rembug Rasa Putri Kedhaton", "WZ9VMkxi0Hk", "video_5"),
            VideoItem("PENGARUH BUDAYA LUAR PADA PENGGUNAAN BATIK", "Y_VDbehFRuo", "video_6")
        )
        val rvSejarah: RecyclerView = findViewById(R.id.rv_sejarah_batik)
        rvSejarah.adapter = VideoAdapter(this, sejarahList)
    }

    private fun watchYoutubeVideo(id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$id"))
        try {
            startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(webIntent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}