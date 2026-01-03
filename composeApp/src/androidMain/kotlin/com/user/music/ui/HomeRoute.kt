package com.user.music.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.user.music.domain.Track
import com.user.music.player.AudioViewModel
import com.user.music.navigation.Route
import com.user.music.player.PlayerState
import com.user.music.ui.home.HomeScreen

import com.user.music.ui.home.HomeUiState
import com.user.music.ui.home.HomeViewModel

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    audioViewModel: AudioViewModel,
    navController: NavController
) {
    val tracks by viewModel.tracks.collectAsState()
    val playerState by audioViewModel.playerState.collectAsState()

    // 🔑 Keep audio playlist in sync with DB-visible tracks
    LaunchedEffect(tracks) {
        if (tracks.isNotEmpty()) {
            audioViewModel.setPlaylist(tracks)
        }
    }

    HomeScreen(
        tracks = tracks,
        isLoading = tracks.isEmpty(),
        playerState = playerState,
        onSortByName = viewModel::sortByName,
        onSortByDuration = viewModel::sortByDuration,
        onLoadMore = viewModel::loadMore,
        onTrackSelected = { track ->
            navController.navigate(
                Route.Audio.create(
                    track.id,
                    track.audioUrl
                )
            )
        },
        onPlayPause = audioViewModel::togglePlayPause,
        onOpenPlayer = {
            val id = playerState.currentTrackId ?: return@HomeScreen
            val url = playerState.currentTrackUrl ?: return@HomeScreen

            navController.navigate(
                Route.Audio.create(id, url)
            )
        },
        trackImage = { track ->
            AsyncImage(
                model = track.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    )
}

