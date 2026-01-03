package com.user.music.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.user.music.audio.AudioViewModel
import com.user.music.navigation.Route
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    audioViewModel: AudioViewModel,
    navController: NavController,


) {
    val uiState by viewModel.uiState.collectAsState()
    val playerState by audioViewModel.playerState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTracks()
    }

    HomeScreen(
        uiState = uiState,
        onSortByName = viewModel::sortByName,
        onSortByDuration = viewModel::sortByDuration,
        onTrackSelected = { track ->
            navController.navigate(
                Route.Audio.create(
                    track.id,
                    track.audioUrl
                )
            )
        }
    )

    MiniPlayer(
        state = playerState,
        onPlayPause = audioViewModel::togglePlayPause,
        onOpenPlayer = {
            navController.navigate(
                Route.Audio.create(
                    playerState.currentTrackId!!,
                    playerState.currentTrackUrl!!
                )
            )
        }
    )
}
