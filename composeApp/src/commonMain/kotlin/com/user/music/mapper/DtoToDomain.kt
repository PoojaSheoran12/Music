package com.user.music.mapper

import com.user.music.domain.Track
import com.user.music.network.dto.TrackDto


fun TrackDto.toDomain(): Track {
    return Track(
        id = id,
        title = name,
        artist = artist_name,
        durationSec = duration,
        audioUrl = audio,
        imageUrl = image
    )
}
