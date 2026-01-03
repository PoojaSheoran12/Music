package com.user.music.data.mapper

import com.user.music.domain.Track
import com.user.music.data.network.dto.TrackDto


fun TrackDto.toDomain(): Track {
    return Track(
        id = id,
        title = name,
        artist = artist_name,
        durationSec = duration,
        audioUrl = audio,
        imageUrl = image ?: album_image ?: ""
    )
}
