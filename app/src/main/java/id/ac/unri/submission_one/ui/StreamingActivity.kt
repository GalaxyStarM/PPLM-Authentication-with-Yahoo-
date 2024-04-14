package id.ac.unri.submission_one.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import id.ac.unri.submission_one.databinding.ActivityStreamingBinding

class StreamingActivity : AppCompatActivity() {

//    private lateinit var playerView: PlayerView
//    private lateinit var exoPlayer: SimpleExoPlayer

    private lateinit var videoView: VideoView

    private var binding : ActivityStreamingBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStreamingBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        videoView = binding?.playerView!!
        initializeVideo()
        videoView.start()
    }

//    private fun initializePlayer(videoUrl: String?) {
//        exoPlayer = SimpleExoPlayer.Builder(this).build()
//        playerView.player = exoPlayer
//
//        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
//        exoPlayer.setMediaItem(mediaItem)
//        exoPlayer.prepare()
//        exoPlayer.play()
//    }

    private fun initializeVideo(){
        val videoUrl = intent.getStringExtra("EXTRA_VIDEO_URL")

        Log.d(TAG, "videoView: " + videoUrl)

        if (videoUrl != null) {
            val uri = Uri.parse(videoUrl)
            val mediaController = MediaController(this)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            videoView.setVideoURI(uri)
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        initializeVideo()
        videoView.start()
    }

    companion object {
        const val EXTRA_VIDEO_URL = "videoUrl"
        const val TAG = "StreamingActivity"
    }
}