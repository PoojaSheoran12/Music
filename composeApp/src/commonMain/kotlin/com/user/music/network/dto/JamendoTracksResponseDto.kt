package com.user.music.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class TracksResponseDto(
    val headers: HeadersDto,
    val results: List<TrackDto>
)