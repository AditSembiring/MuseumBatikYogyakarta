package com.example.museumbatikv1 // Ganti dengan package Anda

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class CollectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        supportActionBar?.title = "Koleksi Museum"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val collectionList = prepareCollectionData()
        val collectionRecyclerView: RecyclerView = findViewById(R.id.rv_collection)

        // 1. Saat membuat adapter, kita teruskan fungsi untuk menangani klik
        collectionRecyclerView.adapter = CollectionAdapter(collectionList) {
            // 'it' adalah CollectionItem yang diklik
            showDetailPopup()
        }
    }

    // 2. Buat fungsi untuk menampilkan pop-up dialog
    private fun showDetailPopup() {
        AlertDialog.Builder(this)
            .setTitle("Informasi Detail")
            .setMessage("Untuk mengetahui lebih detail, silakan kunjungi museum.")
            .setPositiveButton("Tutup") { dialog, _ ->
                dialog.dismiss() // Menutup dialog saat tombol ditekan
            }
            .setCancelable(false) // Mencegah dialog ditutup saat area luar diklik
            .show()
    }

    private fun prepareCollectionData(): List<CollectionItem> {
        // Pastikan Anda memiliki gambar `koleksi_1`, `koleksi_2`, dst. di folder drawable
        return listOf(
            CollectionItem("Batik Parang", "koleksi_1", "Deskripsi untuk Batik Parang..."),
            CollectionItem("Batik Kawung", "koleksi_2", "Deskripsi untuk Batik Kawung..."),
            CollectionItem("Batik Sidomukti", "koleksi_3", "Deskripsi untuk Batik Sidomukti..."),
            CollectionItem("Batik Truntum", "koleksi_4", "Deskripsi untuk Batik Truntum..."),
            CollectionItem("Batik Sekar Jagad", "koleksi_5", "Deskripsi untuk Batik Sekar Jagad..."),
            CollectionItem("Batik Mega Mendung", "koleksi_6", "Deskripsi untuk Batik Mega Mendung...")
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}