package com.example.museumbatikv1

import android.os.Bundle
import androidx.activity.ComponentActivity // atau androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.appcompat.app.AppCompatDelegate
import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout

class MainActivity : ComponentActivity() { // Ganti ke AppCompatActivity jika perlu

    // Inisialisasi ViewModel yang kita buat tadi
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Langkah 1: Panggil installSplashScreen() SEBELUM apapun.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // Langkah 2: Tahan splash screen menggunakan kondisi dari ViewModel.
        // Splash screen akan terus tampil selama viewModel.isLoading.value adalah true.
        splashScreen.setKeepOnScreenCondition {
            viewModel.isLoading.value
        }

        // Langkah 3: Set layout utama Anda.
        // Android akan secara otomatis menampilkannya setelah kondisi di atas terpenuhi (menjadi false).
        setContentView(R.layout.activity_main)

        val promo1ImageView: ImageView = findViewById(R.id.iv_promo1)
        val promo2ImageView: ImageView = findViewById(R.id.iv_promo2)
        val promo3ImageView: ImageView = findViewById(R.id.iv_promo3)
        promo1ImageView.setOnClickListener {
            // Buat Intent untuk membuka PromoDetailActivity
            val intent = Intent(this, PromoDetailActivity::class.java)
            // Kirim data berupa ID unik untuk promo ini
            intent.putExtra(PromoDetailActivity.EXTRA_PROMO_ID, "promo1")
            // Mulai Activity baru
            startActivity(intent)
        }

        // Atur listener untuk promo 2
        promo2ImageView.setOnClickListener {
            val intent = Intent(this, PromoDetailActivity::class.java)
            intent.putExtra(PromoDetailActivity.EXTRA_PROMO_ID, "promo2")
            startActivity(intent)
        }

        // Atur listener untuk promo 3
        promo3ImageView.setOnClickListener {
            val intent = Intent(this, PromoDetailActivity::class.java)
            intent.putExtra(PromoDetailActivity.EXTRA_PROMO_ID, "promo3")
            startActivity(intent)
        }

        val menuNews: LinearLayout = findViewById(R.id.menu_news)
        menuNews.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }

        val menuCollection: LinearLayout = findViewById(R.id.menu_collection)
        menuCollection.setOnClickListener {
            val intent = Intent(this, CollectionActivity::class.java)
            startActivity(intent)
        }

        val menuShop: LinearLayout = findViewById(R.id.menu_shop)
        menuShop.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }

        val menuAbout: LinearLayout = findViewById(R.id.menu_about)
        menuAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        val qrButton: ImageView = findViewById(R.id.qr_button)
        qrButton.setOnClickListener {
            val intent = Intent(this, ScannerActivity::class.java)
            startActivity(intent)
        }

    }
}