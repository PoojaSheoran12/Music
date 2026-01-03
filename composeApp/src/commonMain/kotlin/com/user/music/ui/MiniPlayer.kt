package com.user.music.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.user.music.audio.PlayerState

@Composable
fun MiniPlayer(
    state: PlayerState,
    onPlayPause: () -> Unit,
    onOpenPlayer: () -> Unit
) {
    if (state.currentTrackId == null) return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpenPlayer() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Now Playing",
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onPlayPause) {
            Icon(
                imageVector = if (state.isPlaying)
                    Icons.Default.Pause
                else
                    Icons.Default.PlayArrow,
                contentDescription = null
            )
        }
    }
}
