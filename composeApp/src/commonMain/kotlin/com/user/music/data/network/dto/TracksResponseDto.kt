package com.user.music.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class TracksResponseDto(
    val headers: HeadersDto,
    val results: List<TrackDto>
)