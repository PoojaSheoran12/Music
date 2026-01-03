package com.user.music.player

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
class AudioViewModel(
    private val audioPlayer: AudioPlayer
) : ViewModel() {

    val playerState: StateFlow<PlayerState> = audioPlayer.state

    fun playTrack(trackId: String, url: String) {

        audioPlayer.play(trackId, url)
    }

    fun togglePlayPause() {
        audioPlayer.togglePlayPause()
    }

    fun seekTo(position: Long) {
        audioPlayer.seekTo(position)
    }

    fun release() {
        audioPlayer.release()
    }
}
