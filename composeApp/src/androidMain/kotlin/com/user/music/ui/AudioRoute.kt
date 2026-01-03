package com.user.music.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.user.music.audio.AudioScreen
import com.user.music.audio.AudioViewModel
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent.get
import org.koin.core.context.GlobalContext





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
        onSeek = viewModel::seekTo
    )
}
