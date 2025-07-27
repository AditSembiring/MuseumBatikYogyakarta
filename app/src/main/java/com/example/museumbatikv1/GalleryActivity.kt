package com.example.museumbatikv1 // Ganti dengan package Anda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        supportActionBar?.title = "Galeri Batik"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Kita bisa gunakan kembali data dari halaman Collection
        val galleryList = prepareCollectionData()

        val galleryRecyclerView: RecyclerView = findViewById(R.id.rv_gallery)
        galleryRecyclerView.adapter = GalleryAdapter(this, galleryList)
    }

    private fun prepareCollectionData(): List<CollectionItem> {
        return listOf(
            CollectionItem("Batik Parang", "koleksi_1", "Batik Parang memiliki makna perjuangan yang tidak pernah menyerah, ibarat ombak laut yang tak henti-hentinya bergerak. Motif ini melambangkan kegigihan dan semangat untuk terus berjuang."),
            CollectionItem("Batik Kawung", "koleksi_2", "Motif Kawung terinspirasi dari buah kolang-kaling. Empat lingkaran yang bertemu di tengah melambangkan sumber energi universal dan kesucian hati."),
            CollectionItem("Batik Sidomukti", "koleksi_3", "Berasal dari kata 'sido' (jadi/terlaksana) dan 'mukti' (mulia/bahagia). Filosofinya adalah harapan agar pemakainya mencapai kemuliaan dan kebahagiaan lahir batin."),
            CollectionItem("Batik Truntum", "koleksi_4", "Motif ini diciptakan oleh Ratu Kencana dan melambangkan cinta yang tumbuh kembali. Truntum bermakna menuntun, diharapkan pemakainya bisa menjadi panutan."),
            CollectionItem("Batik Sekar Jagad", "koleksi_5", "Sekar Jagad berarti 'bunga dunia'. Motifnya berisi ragam hias dari berbagai daerah, melambangkan keindahan dan keragaman yang ada di dunia."),
            CollectionItem("Batik Mega Mendung", "koleksi_6", "Berasal dari Cirebon, motif awan ini melambangkan kesabaran, keluasan hati, dan sifat pemimpin yang mengayomi.")
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}