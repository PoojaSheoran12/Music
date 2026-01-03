package com.user.music.audio

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PlayerControls(
    state: PlayerState,
    onPlayPause: () -> Unit,
    onSeek: (Long) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {

       // Text("${formatTime(state.currentPosition)} / ${formatTime(state.duration)}")

        Slider(
            value = state.currentPosition.toFloat(),
            onValueChange = { onSeek(it.toLong()) },
            valueRange = 0f..state.duration.toFloat()
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

        state.error?.let {
            Text(it, color = Color.Red)
        }
    }
}
