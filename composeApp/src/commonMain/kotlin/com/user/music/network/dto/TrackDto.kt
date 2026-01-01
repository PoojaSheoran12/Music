package com.user.music.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class TrackDto(
    val id: String,
    val name: String,
    val duration: Int,
    val artist_name: String,
    val audio: String,
    val image: String?
)