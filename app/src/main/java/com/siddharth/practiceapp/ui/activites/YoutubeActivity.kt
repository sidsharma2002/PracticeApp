package com.siddharth.practiceapp.ui.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.siddharth.practiceapp.databinding.ActivityYoutubeBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import android.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class YoutubeActivity : AppCompatActivity() {

    private var _binding: ActivityYoutubeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
    }

    private fun setupUi() {
        val youTubePlayerView: YouTubePlayerView =
            binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                val videoId = "GOFyEdjzpwI"
                youTubePlayer.loadVideo(videoId, 0F)
                youTubePlayer.play()
            }
        })
    }
}