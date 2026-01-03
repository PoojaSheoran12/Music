package com.user.music.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.user.music.presentation.audioPlayer.AudioScreen
import com.user.music.player.PlayerState
import com.user.music.presentation.audioPlayer.PlayerViewModel


@Composable
fun AudioRoute(
    viewModel: PlayerViewModel,
    trackId: String,

    ) {

    val playerState by viewModel.playerState.collectAsState()

    LaunchedEffect(trackId) {
        viewModel.playTrack(trackId)
    }
 
    AudioScreen(
        playerState = playerState,
        onPlayPause = viewModel::togglePlayPause,
        onSeek = viewModel::seekTo,
        onNext = viewModel::playNext,
        onPrevious = viewModel::playPrevious,
        artwork = {
            TrackArtwork(playerState)
        }
    )
}
@Composable
fun TrackArtwork(playerState: PlayerState) {
    AsyncImage(
        model = playerState.currentArtworkUrl,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}
