package com.example.museumbatikv1 // Ganti dengan package Anda

import android.os.Bundle
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GalleryDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_detail)

        // --- Atur Toolbar Kustom ---
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // -------------------------

        val name = intent.getStringExtra("EXTRA_NAME")
        val imageName = intent.getStringExtra("EXTRA_IMAGE")
        val description = intent.getStringExtra("EXTRA_DESC")

        // Atur judul di CollapsingToolbarLayout agar bisa menghilang saat scroll
        val collapsingToolbar: CollapsingToolbarLayout = findViewById(R.id.toolbar_layout)
        collapsingToolbar.title = name

        val detailImage: ImageView = findViewById(R.id.iv_detail_image)
        val detailName: TextView = findViewById(R.id.tv_detail_name)
        val detailDesc: TextView = findViewById(R.id.tv_detail_description)

        detailName.text = name
        detailDesc.text = description
        if (imageName != null) {
            val imageResId = resources.getIdentifier(imageName, "drawable", packageName)
            detailImage.setImageResource(imageResId)
        }

        val fabShare: FloatingActionButton = findViewById(R.id.fab_share)
        fabShare.setOnClickListener {
            val shareText = "Lihat koleksi ${name} dari Museum Batik Yogyakarta! \n\n${description}"
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}