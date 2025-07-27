package com.example.museumbatikv1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class NewsActivity : AppCompatActivity() {

    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsList: ArrayList<News>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        // Atur judul di ActionBar
        supportActionBar?.title = "Berita & Acara"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Siapkan data berita (untuk contoh)
        prepareNewsData()

        // Atur RecyclerView
        newsRecyclerView = findViewById(R.id.rv_news)
        newsRecyclerView.setHasFixedSize(true)
        newsAdapter = NewsAdapter(newsList)
        newsRecyclerView.adapter = newsAdapter
    }

    private fun prepareNewsData() {
        newsList = ArrayList()
        // Anda perlu menyiapkan gambar promo1, promo2, promo3 di folder drawable
        newsList.add(
            News(
                "Pemkot Yogya Integrasikan Seni Membatik dengan Kurikulum Sekolah",
                "14 Oktober 2024",
                "berita1",
                "Detail konten berita 1..."
            )
        )
        newsList.add(
            News(
                "Puluhan Siswa Purna Paskibra Eksplorasi Ragam Wisata dan Budaya di Jogja",
                "16 Oktober 2024",
                "berita2",
                "Detail konten berita 2..."
            )
        )
        newsList.add(
            News(
                "Museum Batik Yogyakarta Kenalkan Batik di Abad ke 18",
                "30 Mei 2024",
                "berita3",
                "Detail konten berita 3..."
            )
        )
    }

    // Fungsi untuk tombol kembali di ActionBar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}