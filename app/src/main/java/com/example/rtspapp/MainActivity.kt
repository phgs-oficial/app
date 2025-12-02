
package com.example.rtspapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.rtsp.RtspMediaSource
import com.example.rtspapp.databinding.ActivityMainBinding
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlay.setOnClickListener {
            val url = binding.inputUrl.text.toString()
            if (url.isNotBlank()) startRTSP(url)
        }
    }

    private fun startRTSP(url: String) {
        player?.release()
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player

        val mediaItem = MediaItem.fromUri(url)
        player!!.setMediaItem(mediaItem)
        player!!.prepare()
        player!!.play()

        player!!.addListener(object : com.google.android.exoplayer2.Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                Toast.makeText(applicationContext, "Erro ao reproduzir RTSP", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroy() {
        player?.release()
        super.onDestroy()
    }
}
