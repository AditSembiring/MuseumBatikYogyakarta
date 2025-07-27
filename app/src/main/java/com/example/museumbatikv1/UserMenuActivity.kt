package com.example.museumbatikv1 // Ganti dengan package Anda

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.util.concurrent.TimeUnit

class UserMenuActivity : AppCompatActivity() {

    private var countdownTimer: CountDownTimer? = null
    private lateinit var countdownTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_menu)

        supportActionBar?.title = "Menu Pengunjung"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        setupMenuClickListeners()
        startCountdownTimer()
    }

    private fun setupMenuClickListeners() {
        val menuUserGallery: CardView = findViewById(R.id.menu_user_gallery)
        val menuVideo: CardView = findViewById(R.id.menu_video)
        val menuQuiz: CardView = findViewById(R.id.menu_quiz)
        val menuShop: CardView = findViewById(R.id.menu_extra_shop)
        val menuAbout: CardView = findViewById(R.id.menu_extra_about)

        findViewById<CardView>(R.id.menu_photo_booth).setOnClickListener {
            startActivity(Intent(this, PhotoBoothActivity::class.java))
        }

        val menuPhotoBooth: CardView = findViewById(R.id.menu_photo_booth)
        menuPhotoBooth.setOnClickListener {
            startActivity(Intent(this, PhotoBoothActivity::class.java))
        }
        menuUserGallery.setOnClickListener {
            startActivity(Intent(this, GalleryActivity::class.java))
        }
        menuVideo.setOnClickListener {
            startActivity(Intent(this, VideoActivity::class.java))
        }
        menuQuiz.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }
        menuShop.setOnClickListener {
            startActivity(Intent(this, ShopActivity::class.java))
        }
        menuAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }

    private fun startCountdownTimer() {
        countdownTextView = findViewById(R.id.tv_countdown_timer)
        val fourHoursInMillis = 4 * 60 * 60 * 1000L

        countdownTimer = object : CountDownTimer(fourHoursInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                countdownTextView.text = timeString
            }

            override fun onFinish() {
                countdownTextView.text = "Sesi berakhir!"
                val intent = Intent(this@UserMenuActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countdownTimer?.cancel()
    }
}