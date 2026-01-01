package com.user.music.domain


data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val durationSec: Int,
    val audioUrl: String,
    val imageUrl: String?
)
