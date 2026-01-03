package com.user.music.player

import kotlinx.coroutines.flow.StateFlow

expect class AudioPlayer {

    val state: StateFlow<PlayerState>

    fun play(trackId: String, url: String)
    fun togglePlayPause()
    fun seekTo(position: Long)
    fun release()
}
