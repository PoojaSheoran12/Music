package com.user.music.player

data class PlayerState(
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val currentTrackId: String? = null,
    val error: String? = null,
    val currentTrackUrl: String? = null,
)
