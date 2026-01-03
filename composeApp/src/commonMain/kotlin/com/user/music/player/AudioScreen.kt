package com.user.music.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.user.music.ui.components.PlayerErrorOverlay


@Composable
fun AudioScreen(
    playerState: PlayerState,
    onPlayPause: () -> Unit,
    onSeek: (Long) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    artwork: @Composable () -> Unit
) {
    var sliderPosition by remember { mutableStateOf(0f) }
    var isUserSeeking by remember { mutableStateOf(false) }

    LaunchedEffect(playerState.currentPosition) {
        if (!isUserSeeking) {
            sliderPosition = playerState.currentPosition.toFloat()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        artwork()
        DarkOverlay()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TrackInfo(playerState)

            PlayerControls(
                playerState = playerState,
                sliderPosition = sliderPosition,
                isUserSeeking = isUserSeeking,
                onSliderChange = {
                    isUserSeeking = true
                    sliderPosition = it
                },
                onSeekFinished = {
                    isUserSeeking = false
                    onSeek(sliderPosition.toLong())
                },
                onPlayPause = onPlayPause,
                onNext = onNext,
                onPrevious = onPrevious
            )
        }

        playerState.error?.let { message ->
            PlayerErrorOverlay(message)
        }
    }
}

@Composable
fun DarkOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black.copy(alpha = 0.6f),
                        Color.Black.copy(alpha = 0.9f)
                    )
                )
            )
    )
}

