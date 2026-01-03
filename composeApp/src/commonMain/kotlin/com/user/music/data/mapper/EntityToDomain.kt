package com.user.music.data.mapper

import com.user.music.domain.Track
import com.user.music.database.Tracks



fun Tracks.toDomain(): Track =
    Track(
        id = id,
        title = title,
        artist = artist,
        durationSec = duration.toInt(),
        imageUrl = imageUrl,
        audioUrl = audioUrl
    )
