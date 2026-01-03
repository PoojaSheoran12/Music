package com.user.music.player
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.user.music.util.formatTime

@Composable
fun PlayerControls(
    playerState: PlayerState,
    sliderPosition: Float,
    isUserSeeking: Boolean,
    onSliderChange: (Float) -> Unit,
    onSeekFinished: () -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    Column {

        Slider(
            value = sliderPosition,
            onValueChange = onSliderChange,
            onValueChangeFinished = onSeekFinished,
            valueRange = 0f..playerState.duration.toFloat(),
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(formatTime(sliderPosition.toLong()), color = Color.White)
            Text(formatTime(playerState.duration), color = Color.White)
        }

        Spacer(Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onPrevious) {
                Icon(Icons.Default.SkipPrevious, null, tint = Color.White)
            }

            IconButton(onClick = onPlayPause) {
                Icon(
                    if (playerState.isPlaying)
                        Icons.Default.Pause
                    else
                        Icons.Default.PlayArrow,
                    null,
                    tint = Color.White
                )
            }

            IconButton(onClick = onNext) {
                Icon(Icons.Default.SkipNext, null, tint = Color.White)
            }
        }
    }
}
