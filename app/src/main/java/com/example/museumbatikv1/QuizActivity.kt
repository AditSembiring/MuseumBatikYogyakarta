package com.example.museumbatikv1 // Ganti dengan package Anda

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var questionTextView: TextView
    private lateinit var questionCounterTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var lifeImageViews: List<ImageView>
    private lateinit var optionButtons: List<Button>

    private lateinit var questionList: List<Question>
    private var currentQuestionIndex = 0
    private var score = 0
    private var lives = 3

    private var bgMusicPlayer: MediaPlayer? = null
    private var sfxPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        supportActionBar?.hide()

        // Inisialisasi View
        questionTextView = findViewById(R.id.tv_question)
        questionCounterTextView = findViewById(R.id.tv_question_counter)
        progressBar = findViewById(R.id.progress_bar)
        lifeImageViews = listOf(
            findViewById(R.id.iv_life1),
            findViewById(R.id.iv_life2),
            findViewById(R.id.iv_life3)
        )
        optionButtons = listOf(
            findViewById(R.id.btn_option1),
            findViewById(R.id.btn_option2),
            findViewById(R.id.btn_option3),
            findViewById(R.id.btn_option4)
        )

        // Set OnClickListener untuk semua tombol pilihan
        optionButtons.forEach { it.setOnClickListener(this) }

        // Siapkan pertanyaan dan mulai kuis
        prepareQuestions()
        showNextQuestion()
        startBackgroundMusic()
    }

    override fun onClick(view: View?) {
        // Nonaktifkan semua tombol agar tidak bisa diklik lagi
        optionButtons.forEach { it.isEnabled = false }

        val selectedButton = view as Button
        val selectedOptionIndex = optionButtons.indexOf(selectedButton)
        checkAnswer(selectedOptionIndex, selectedButton)
    }

    private fun checkAnswer(selectedIndex: Int, selectedButton: Button) {
        if (selectedIndex == questionList[currentQuestionIndex].correctAnswerIndex) {
            // Jawaban Benar
            score++
            selectedButton.setBackgroundColor(Color.GREEN)
            playSoundEffect(R.raw.correctsfx)
        } else {
            // Jawaban Salah
            lives--
            updateLives()
            selectedButton.setBackgroundColor(Color.RED)
            // Tunjukkan jawaban yang benar
            optionButtons[questionList[currentQuestionIndex].correctAnswerIndex].setBackgroundColor(Color.GREEN)
            playSoundEffect(R.raw.wrongsfx)
        }

        // Delay sebelum lanjut ke pertanyaan berikutnya atau selesai
        Handler(Looper.getMainLooper()).postDelayed({
            if (lives == 0 || currentQuestionIndex == questionList.size - 1) {
                endQuiz()
            } else {
                currentQuestionIndex++
                showNextQuestion()
            }
        }, 1500) // Delay 1.5 detik
    }

    private fun showNextQuestion() {
        // Reset tampilan tombol
        optionButtons.forEach {
            it.isEnabled = true
            it.setBackgroundColor(Color.parseColor("#c2802f"))
        }

        val question = questionList[currentQuestionIndex]
        questionTextView.text = question.questionText
        for (i in optionButtons.indices) {
            optionButtons[i].text = question.options[i]
        }
        questionCounterTextView.text = "Pertanyaan ${currentQuestionIndex + 1}/${questionList.size}"
        progressBar.progress = score
    }

    private fun updateLives() {
        for (i in lifeImageViews.indices) {
            lifeImageViews[i].visibility = if (i < lives) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun endQuiz() {
        val intent = Intent(this, QuizResultActivity::class.java)
        intent.putExtra("FINAL_SCORE", score)
        intent.putExtra("TOTAL_QUESTIONS", questionList.size)
        startActivity(intent)
        finish() // Tutup activity kuis
    }

    private fun startBackgroundMusic() {
        bgMusicPlayer = MediaPlayer.create(this, R.raw.bgsquiz)
        bgMusicPlayer?.isLooping = true
        bgMusicPlayer?.start()
    }

    private fun playSoundEffect(soundId: Int) {
        sfxPlayer?.release()
        sfxPlayer = MediaPlayer.create(this, soundId)
        sfxPlayer?.start()
    }

    override fun onStop() {
        super.onStop()
        bgMusicPlayer?.pause()
    }

    override fun onRestart() {
        super.onRestart()
        bgMusicPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        bgMusicPlayer?.release()
        sfxPlayer?.release()
    }

    private fun prepareQuestions() {
        questionList = listOf(
            Question("Motif batik yang melambangkan harapan agar pemakainya mencapai kemuliaan adalah...",
                listOf("Parang", "Kawung", "Sidomukti", "Truntum"), 2),
            Question("Dari daerah manakah motif batik Mega Mendung berasal?",
                listOf("Yogyakarta", "Solo", "Pekalongan", "Cirebon"), 3),
            Question("Apa ciri khas utama batik Yogyakarta dibandingkan batik dari daerah lain?",
                listOf("Menggunakan warna cerah seperti merah muda dan oranye", "Didominasi warna gelap seperti hitam dan ungu", "Warna dominan putih, hitam, dan coklat sogan", "Motifnya selalu berbentuk hewan mitologi"), 2),
            Question("Motif batik Yogyakarta yang melambangkan kekuasaan dan hanya boleh digunakan oleh keluarga kerajaan adalah?",
                listOf("Parang Rusak", "Kawung", "Sekar Jagad", "Truntum"), 0),
            Question("Motif Kawung pada batik Yogyakarta memiliki makna...",
                listOf("Keberanian dan kekuatan", "Kesucian dan pengendalian diri", "Kekayaan dan kejayaan", "Cinta dan kasih sayang"), 1),
            Question("Daerah di Yogyakarta yang terkenal sebagai sentra batik tradisional adalah...",
                listOf("Kotagede", "Malioboro", "Taman Sari", "Kampung Batik Giriloyo"), 3),
            Question("Motif batik yang menggambarkan kehidupan yang harmonis dan damai adalah...",
                listOf("Lereng", "Sidomukti", "Ceplok", "Nitik"), 1),
            Question("Fungsi batik dalam budaya keraton Yogyakarta antara lain adalah...",
                listOf("Sebagai bahan dagangan ekspor", "Sebagai pelengkap upacara adat dan simbol status sosial", "Untuk pakaian sehari-hari saja", "Sebagai oleh-oleh wisatawan"), 1),
            Question("Batik motif Truntum biasanya dikenakan pada acara...",
                listOf("Pemakaman", "Pernikahan oleh orang tua pengantin", "Pesta ulang tahun", "Hari kemerdekaan"), 1),
            Question("Ciri teknik batik tulis dari Yogyakarta adalah...",
                listOf("Menggunakan cetakan untuk membuat motif", "Dikerjakan dengan tangan menggunakan canting", "Dicetak dengan mesin modern", "Dilukis dengan kuas besar"), 1),
            // ... Tambahkan 8 pertanyaan lainnya di sini
        )
    }
}