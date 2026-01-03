package com.user.music.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.user.music.player.AudioScreen
import com.user.music.player.AudioViewModel


@Composable
fun AudioRoute(
    viewModel: AudioViewModel,
    trackId: String,
    audioUrl: String,

) {

    val playerState by viewModel.playerState.collectAsState()

    LaunchedEffect(trackId) {
        viewModel.playTrack(trackId, audioUrl)
    }
 
    AudioScreen(
        playerState = playerState,
        onPlayPause = viewModel::togglePlayPause,
        onSeek = viewModel::seekTo,
        onNext = viewModel::playNext,
        onPrevious = viewModel::playPrevious
    )
}
