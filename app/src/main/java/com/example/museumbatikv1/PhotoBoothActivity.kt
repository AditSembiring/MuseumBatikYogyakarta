package com.example.museumbatikv1

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PhotoBoothActivity : AppCompatActivity() {

    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var currentFrameResId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_booth)
        supportActionBar?.hide()

        cameraExecutor = Executors.newSingleThreadExecutor()

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        setupFrameSelector()
        findViewById<ImageButton>(R.id.btn_capture).setOnClickListener { takePhoto() }
        findViewById<ImageButton>(R.id.btn_switch_camera).setOnClickListener { switchCamera() }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(findViewById<PreviewView>(R.id.viewFinder).surfaceProvider)
            }
            imageCapture = ImageCapture.Builder().build()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Log.e(TAG, "Gagal memulai kamera", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun switchCamera() {
        cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }
        startCamera()
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        imageCapture.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
                override fun onCaptureSuccess(image: ImageProxy) {
                    val matrix = Matrix().apply {
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                        if (cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
                            postScale(-1f, 1f)
                        }
                    }
                    val imageBitmap = image.image!!.toBitmap()
                    val rotatedBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.width, imageBitmap.height, matrix, true)

                    if (currentFrameResId != null && currentFrameResId != 0) {
                        val frameBitmap = BitmapFactory.decodeResource(resources, currentFrameResId!!)
                        val resultBitmap = Bitmap.createBitmap(rotatedBitmap.width, rotatedBitmap.height, rotatedBitmap.config ?: Bitmap.Config.ARGB_8888)
                        val canvas = Canvas(resultBitmap)
                        canvas.drawBitmap(rotatedBitmap, 0f, 0f, null)
                        canvas.drawBitmap(frameBitmap, null, Rect(0, 0, rotatedBitmap.width, rotatedBitmap.height), null)
                        saveBitmapToGallery(resultBitmap)
                    } else {
                        saveBitmapToGallery(rotatedBitmap)
                    }
                    image.close()
                }

                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(baseContext, "Gagal mengambil foto.", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun setupFrameSelector() {
        val frameOverlay: ImageView = findViewById(R.id.iv_frame_overlay)
        val rvFrames: RecyclerView = findViewById(R.id.rv_frames)
        val frames = listOf(0, R.drawable.frame_1, R.drawable.frame_2, R.drawable.frame_3)

        rvFrames.adapter = FrameAdapter(frames) { selectedFrameResId ->
            if (selectedFrameResId == 0) {
                frameOverlay.setImageDrawable(null)
                currentFrameResId = null
            } else {
                frameOverlay.setImageResource(selectedFrameResId)
                currentFrameResId = selectedFrameResId
            }
        }
    }

    private fun saveBitmapToGallery(bitmap: Bitmap) {
        val name = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US).format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MuseumBatik")
            }
        }
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        try {
            contentResolver.openOutputStream(uri!!)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                runOnUiThread { Toast.makeText(this, "Foto berhasil disimpan!", Toast.LENGTH_SHORT).show() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
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
        private const val TAG = "PhotoBoothActivity"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}

fun android.media.Image.toBitmap(): Bitmap {
    val buffer = planes[0].buffer
    buffer.rewind()
    val bytes = ByteArray(buffer.capacity())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}