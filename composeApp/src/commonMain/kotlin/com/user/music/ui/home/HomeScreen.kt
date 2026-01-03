package com.user.music.ui.home

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.user.music.player.PlayerState
import com.user.music.domain.Track
import com.user.music.ui.components.MiniPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState<List<Track>>,
    playerState: PlayerState,
    onSortByName: () -> Unit,
    onSortByDuration: () -> Unit,
    onTrackSelected: (Track) -> Unit,
    onPlayPause: () -> Unit,
    onOpenPlayer: () -> Unit
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
                onOpenPlayer = onOpenPlayer
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (uiState) {

                is HomeUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is HomeUiState.Error -> {
                    Text(
                        text = uiState.message,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is HomeUiState.Success -> {
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
                                bottom = 72.dp // 🔑 space for MiniPlayer
                            ),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(uiState.data) { track ->
                                TrackItem(
                                    track = track,
                                    onClick = { onTrackSelected(track) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



