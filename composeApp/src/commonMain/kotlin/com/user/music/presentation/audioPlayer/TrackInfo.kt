package com.user.music.presentation.audioPlayer

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.user.music.player.PlayerState

@Composable
fun TrackInfo(playerState: PlayerState) {
    Text(
        text = playerState.currentTrackId ?: "Now Playing",
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        maxLines = 1
    )
}
