// PromoDetailActivity.kt
package com.example.museumbatikv1
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PromoDetailActivity : AppCompatActivity() {

    // Ini adalah 'kunci' untuk extra data yang kita kirim.
    // Menaruhnya di sini adalah praktik yang baik.
    companion object {
        const val EXTRA_PROMO_ID = "extra_promo_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo_detail)

        // Aktifkan tombol kembali di ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Cari view dari layout detail
        val imageView: ImageView = findViewById(R.id.iv_promo_detail_image)
        val titleView: TextView = findViewById(R.id.tv_promo_detail_title)
        val descriptionView: TextView = findViewById(R.id.tv_promo_detail_description)

        // Ambil ID promo yang dikirim dari MainActivity
        val promoId = intent.getStringExtra(EXTRA_PROMO_ID)

        // Gunakan 'when' untuk menentukan konten apa yang harus ditampilkan
        when (promoId) {
            "promo1" -> {
                imageView.setImageResource(R.drawable.promo1)
                titleView.text = "Spesial Hari Batik Nasional"
                descriptionView.text = "Kenakan pakaian batik saat berbelanja dan nikmati diskon 10% untuk semua produk!\n" +
                        "\n" +
                        "\uD83D\uDCCC Syarat & Ketentuan:\n" +
                        "\n" +
                        "Berlaku untuk pembelian minimal Rp200.000\n" +
                        "\n" +
                        "Pelanggan wajib mengenakan pakaian batik saat berbelanja\n" +
                        "\n" +
                        "Berlaku untuk semua produk\n" +
                        "\n" +
                        "Promo berlaku selama periode Hari Batik Nasional\n" +
                        "\n" +
                        "Jangan lewatkan kesempatan spesial ini! Tunjukkan cinta pada batik dan dapatkan penawaran menariknya"
                supportActionBar?.title = "Rayakan Hari Batik Nasional bersama kami!"
            }
            "promo2" -> {
                imageView.setImageResource(R.drawable.promo2)
                titleView.text = "Promo Spesial: Koleksi Batik Malang"
                descriptionView.text = "\uD83D\uDFE2 Buy 1 Get 1 Free\n" +
                        "Beli satu produk Batik Malang, dapatkan satu lagi GRATIS (dengan harga yang sama atau lebih rendah)!\n" +
                        "\n" +
                        "ATAU\n" +
                        "\n" +
                        "\uD83D\uDD35 Diskon 30%\n" +
                        "Dapatkan potongan harga langsung sebesar 30% untuk semua koleksi Batik Malang!\n" +
                        "\n" +
                        "\uD83D\uDCCC Syarat & Ketentuan:\n" +
                        "\n" +
                        "Promo tidak dapat digabungkan\n" +
                        "\n" +
                        "Berlaku untuk produk Batik Malang tertentu\n" +
                        "\n" +
                        "Selama persediaan masih ada\n" +
                        "\n" +
                        "Promo berlaku hingga [masukkan tanggal berakhirnya]"
                supportActionBar?.title = "Pilih salah satu promo menarik berikut ini untuk koleksi Batik Malang:"
            }
            "promo3" -> {
                imageView.setImageResource(R.drawable.promo3)
                titleView.text = "Promo Lezat Spesial Batik!"
                descriptionView.text = "Nikmati pilihan menu makanan favorit dengan harga spesial, dan dapatkan gratis 1 minuman Teh Lemon Ginger Honey jika kamu mengenakan pakaian batik!\n" +
                        "\n" +
                        "\uD83E\uDD58 Menu Promo:\n" +
                        "\n" +
                        "Nasi Goreng Batik\n" +
                        "\n" +
                        "Mie Ayam Malang\n" +
                        "\n" +
                        "Sate Lilit Nusantara\n" +
                        "\n" +
                        "dan masih banyak lagi!\n" +
                        "\n" +
                        "\uD83C\uDF79 Bonus Minuman:\n" +
                        "Tunjukkan batikmu dan dapatkan 1 Teh Lemon Ginger Honey GRATIS untuk setiap transaksi!\n" +
                        "\n" +
                        "\uD83D\uDCCC Syarat & Ketentuan:\n" +
                        "\n" +
                        "Berlaku hanya untuk pelanggan yang mengenakan batik\n" +
                        "\n" +
                        "Gratis minuman hanya untuk pembelian menu promo\n" +
                        "\n" +
                        "Tidak berlaku untuk layanan delivery\n" +
                        "\n" +
                        "Promo berlaku selama periode Hari Batik"
                supportActionBar?.title = "Makan Enak, Dapat Minuman Gratis!"
            }
        }
    }

    // Fungsi ini akan dipanggil saat tombol kembali di ActionBar ditekan
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}