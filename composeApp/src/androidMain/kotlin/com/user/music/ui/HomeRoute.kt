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
import com.user.music.player.PlayerViewModel
import com.user.music.navigation.Route
import com.user.music.ui.home.HomeScreen

import com.user.music.ui.home.HomeViewModel

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    audioViewModel: PlayerViewModel,
    navController: NavController
) {
    val tracks by viewModel.tracks.collectAsState()
    val playerState by audioViewModel.playerState.collectAsState()


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
                Route.Audio.create(track.id)
            )
        },
        onPlayPause = audioViewModel::togglePlayPause,
        onOpenPlayer = {
            val id = playerState.currentTrackId ?: return@HomeScreen
            navController.navigate(Route.Audio.create(id))
        },


        trackImage = { track ->
            AsyncImage(
                model = track.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        },


        miniPlayerArtwork = {
            AsyncImage(
                model = playerState.currentArtworkUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    )
}
