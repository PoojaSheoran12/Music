package com.user.music.player

data class PlayerState(
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val currentTrackId: String? = null,
    val currentTrackTitle: String? = null,
    val currentTrackUrl: String? = null,
    val currentArtworkUrl: String? = null,
    val error: String? = null,
)