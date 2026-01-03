package com.user.music.audio

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
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.user.music.ui.HomeViewModel
import org.koin.mp.KoinPlatform.getKoin


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

fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "$minutes:${seconds.toString().padStart(2, '0')}"
}


//@Composable
//fun AudioScreen(
//    trackId: String,
//    audioUrl: String
//) {
//    val viewModel = remember {
//        getKoin().get<AudioViewModel>()
//    }
//
//    val playerState by viewModel.playerState.collectAsState()
//
//    LaunchedEffect(trackId) {
//        viewModel.playTrack(trackId, audioUrl)
//    }
//
//    PlayerControls(
//        state = playerState,
//        onPlayPause = viewModel::togglePlayPause,
//        onSeek = viewModel::seekTo
//    )
//}


//@Composable
//fun AudioScreen(
////    viewModel: AudioViewModel = koinViewModel()
//) {
//    val viewModel = remember {
//        getKoin().get<AudioViewModel>()
//    }
//
//    val playerState by viewModel.playerState.collectAsState()
//
//    PlayerControls(
//        state = playerState,
//        onPlayPause = viewModel::togglePlayPause,
//        onSeek = viewModel::seekTo
//    )
//}
