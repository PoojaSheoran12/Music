package com.user.music.presentation.home

import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.user.music.player.PlayerState
import com.user.music.domain.model.Track
import com.user.music.presentation.components.ErrorState
import com.user.music.presentation.components.HomeHeader
import com.user.music.presentation.components.LoadingState
import com.user.music.presentation.components.MiniPlayer

@Composable
fun HomeScreen(
    tracks: List<Track>,
    uiState: HomeUiState,
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B0B10),
                        Color(0xFF141420),
                        Color(0xFF0F0F14)
                    )
                )
            )
    ) {

        Column {


            HomeHeader()

            when (uiState) {

                HomeUiState.Loading -> {
                    LoadingState()
                }

                is HomeUiState.Error -> {
                    ErrorState(
                        message = uiState.message,
                        onRetry = onLoadMore
                    )
                }

                HomeUiState.Idle -> {


                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White.copy(alpha = 0.06f)
                    ) {
                        SortSection(
                            selected = SortMode.NONE,
                            onSortByName = onSortByName,
                            onSortByDuration = onSortByDuration
                        )
                    }

                    Spacer(Modifier.height(12.dp))


                    Text(
                        text = "All Tracks",
                        modifier = Modifier.padding(
                            start = 20.dp,
                            bottom = 8.dp
                        ),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White.copy(alpha = 0.7f)
                    )


                    LazyColumn(
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 120.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
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

        if (playerState.currentTrackId != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.08f),
                    shadowElevation = 12.dp
                ) {
                    MiniPlayer(
                        state = playerState,
                        onPlayPause = onPlayPause,
                        onOpenPlayer = onOpenPlayer,
                        artwork = miniPlayerArtwork
                    )
                }
            }
        }
    }
}
