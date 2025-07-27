package com.example.museumbatikv1 // Ganti dengan package Anda

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.content.Intent

class ScannerActivity : AppCompatActivity() {

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraSelector: CameraSelector
    private var camera: Camera? = null
    private var isFlashOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        cameraExecutor = Executors.newSingleThreadExecutor()

        // Minta Izin Kamera
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Setup Tombol
        findViewById<ImageButton>(R.id.btn_back).setOnClickListener { finish() }
        findViewById<ImageButton>(R.id.btn_flash).setOnClickListener { toggleFlash() }
    }

    private fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(findViewById<PreviewView>(R.id.viewFinder).surfaceProvider)
            }

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, QrCodeAnalyzer { qrCode ->
                        // Hentikan analisis agar tidak memproses berulang kali
                        it.clearAnalyzer()
                        // Tampilkan hasil di UI Thread
                        runOnUiThread { handleQrCode(qrCode) }
                    })
                }

            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalysis
                )
            } catch (exc: Exception) {
                // Gagal memulai kamera
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun handleQrCode(qrCode: String) {
        // Logika untuk dummy QR
        when (qrCode) {
            "MUSEUM_BATIK_VALID_TICKET_123" -> {
                // Jika QR valid, buka UserMenuActivity
                Toast.makeText(this, "QR Valid! Selamat Datang.", Toast.LENGTH_SHORT).show()

                // Buat Intent ke UserMenuActivity
                val intent = Intent(this, UserMenuActivity::class.java)
                // Flag ini akan membersihkan halaman scanner dari riwayat,
                // sehingga tombol 'back' akan langsung keluar dari aplikasi
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

                finish() // Tutup halaman scanner
            }
            else -> {
                // Jika QR tidak valid, tampilkan pop-up
                showInvalidQrPopup()
            }
        }
    }

    private fun showInvalidQrPopup() {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage("Kode QR tidak valid atau sudah kedaluwarsa.")
            .setPositiveButton("Close") { dialog, _ ->
                dialog.dismiss()
                // Mulai lagi analisis setelah dialog ditutup
                startCamera()
            }
            .setCancelable(false)
            .show()
    }

    private fun toggleFlash() {
        isFlashOn = !isFlashOn
        camera?.cameraControl?.enableTorch(isFlashOn)
        val flashButton: com.google.android.material.floatingactionbutton.FloatingActionButton = findViewById(R.id.btn_flash)

        if (isFlashOn) {
            flashButton.setImageResource(R.drawable.ic_flash_on)
        } else {
            flashButton.setImageResource(R.drawable.ic_flash_off)
        }
    }

    // Boilerplate untuk izin
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "Izin kamera tidak diberikan.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}


// Kelas helper untuk menganalisis QR
class QrCodeAnalyzer(private val onQrCodeScanned: (String) -> Unit) : ImageAnalysis.Analyzer {
    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()
    private val scanner: BarcodeScanner = BarcodeScanning.getClient(options)

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        barcodes.firstOrNull()?.rawValue?.let {
                            onQrCodeScanned(it)
                        }
                    }
                }
                .addOnFailureListener {
                    // Gagal memproses
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }
}