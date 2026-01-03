package com.user.music.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.user.music.util.formatTime


@Composable
fun AudioScreen(
    playerState: PlayerState,
    onPlayPause: () -> Unit,
    onSeek: (Long) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(0f) }
    var isUserSeeking by remember { mutableStateOf(false) }

    // Sync slider with player ONLY when user is not dragging
    LaunchedEffect(playerState.currentPosition) {
        if (!isUserSeeking) {
            sliderPosition = playerState.currentPosition.toFloat()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = playerState.currentTrackId ?: "Now Playing",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(24.dp))

        Slider(
            value = sliderPosition,
            onValueChange = {
                isUserSeeking = true
                sliderPosition = it
            },
            onValueChangeFinished = {
                isUserSeeking = false
                onSeek(sliderPosition.toLong())
            },
            valueRange = 0f..playerState.duration.toFloat()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(formatTime(sliderPosition.toLong()))
            Text(formatTime(playerState.duration))
        }

        Spacer(Modifier.height(24.dp))

        IconButton(
            onClick = onPlayPause,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = if (playerState.isPlaying)
                    Icons.Default.Pause
                else
                    Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        }
    }

}


