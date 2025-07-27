package com.example.museumbatikv1 // Ganti dengan package Anda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class QuizResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)
        supportActionBar?.hide()

        val score = intent.getIntExtra("FINAL_SCORE", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 10)

        val resultIcon: ImageView = findViewById(R.id.iv_result_icon)
        val resultTitle: TextView = findViewById(R.id.tv_result_title)
        val finalScoreText: TextView = findViewById(R.id.tv_final_score)
        val voucherCard: CardView = findViewById(R.id.voucher_card)
        val replayButton: Button = findViewById(R.id.btn_replay)
        val backToMenuButton: Button = findViewById(R.id.btn_back_to_menu)

        finalScoreText.text = "Skor Akhir Anda: $score / $totalQuestions"

        // Cek jika menang sempurna
        if (score == totalQuestions) {
            resultIcon.setImageResource(R.drawable.ic_trophy)
            resultTitle.text = "Luar Biasa!"
            voucherCard.visibility = View.VISIBLE
        } else {
            resultIcon.setImageResource(R.drawable.ic_gameover)
            resultTitle.text = "Coba Lagi, ya!"
            voucherCard.visibility = View.GONE
        }

        // Logika untuk replay 1x
        val sharedPref = getSharedPreferences("QuizApp", Context.MODE_PRIVATE)
        val hasReplayed = sharedPref.getBoolean("hasReplayed", false)

        if (hasReplayed) {
            replayButton.isEnabled = false
            replayButton.text = "Kesempatan Mengulang Habis"
        }

        replayButton.setOnClickListener {
            with(sharedPref.edit()) {
                putBoolean("hasReplayed", true)
                apply()
            }
            startActivity(Intent(this, QuizActivity::class.java))
            finish()
        }

        backToMenuButton.setOnClickListener {
            // Kembali ke menu utama, bukan UserMenuActivity
            val intent = Intent(this, UserMenuActivity::class.java)
            startActivity(intent)
            finish() // Tutup halaman hasil
        }
    }
}