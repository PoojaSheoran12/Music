package com.user.music.ui.home

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.user.music.player.PlayerState
import com.user.music.domain.model.Track
import com.user.music.ui.components.MiniPlayer
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    tracks: List<Track>,
    isLoading: Boolean,
    playerState: PlayerState,
    onSortByName: () -> Unit,
    onSortByDuration: () -> Unit,
    onLoadMore: () -> Unit,
    onTrackSelected: (Track) -> Unit,
    onPlayPause: () -> Unit,
    onOpenPlayer: () -> Unit,
    trackImage: @Composable (Track) -> Unit,
    miniPlayerArtwork: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Music Library") }
            )
        },
        bottomBar = {
            MiniPlayer(
                state = playerState,
                onPlayPause = onPlayPause,
                onOpenPlayer = onOpenPlayer,
                artwork = { miniPlayerArtwork() }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                return@Box
            }

            Column {

                SortSection(
                    onSortByName = onSortByName,
                    onSortByDuration = onSortByDuration
                )

                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 12.dp,
                        end = 12.dp,
                        top = 12.dp,
                        bottom = 72.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(tracks) { track ->
                        TrackItem(
                            track = track,
                            onClick = { onTrackSelected(track) },
                            image = { trackImage(track) }
                        )
                    }

                    item {
                        LaunchedEffect(Unit) {
                            onLoadMore()
                        }
                    }
                }
            }
        }
    }
}
