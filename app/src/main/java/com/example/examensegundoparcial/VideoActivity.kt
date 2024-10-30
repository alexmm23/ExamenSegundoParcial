package com.example.examensegundoparcial

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

class VideoActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private var videoUri: Uri? = null

    private val recordVideo = registerForActivityResult(ActivityResultContracts.CaptureVideo()) { success: Boolean ->
        if (success) {
            videoView.setVideoURI(videoUri)
            videoView.start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        videoView = findViewById(R.id.videoView)
        val btnRecordVideo: Button = findViewById(R.id.btnRecordVideo)

        btnRecordVideo.setOnClickListener {
            recordVideo()
        }
    }

    private fun recordVideo() {
        val videoFile = File(getExternalFilesDir(Environment.DIRECTORY_MOVIES), "video_${System.currentTimeMillis()}.mp4")
        videoUri = FileProvider.getUriForFile(this, "com.example.examensegundoparcial.fileprovider", videoFile)
        recordVideo.launch(videoUri)
    }
}