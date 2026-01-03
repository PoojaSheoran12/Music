package com.user.music.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.user.music.audio.PlayerState
import com.user.music.domain.Track
import kotlinx.coroutines.flow.StateFlow
import org.koin.mp.KoinPlatform.getKoin

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

@Composable
private fun SortSection(
    onSortByName: () -> Unit,
    onSortByDuration: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = false,
            onClick = onSortByName,
            label = { Text("A–Z") }
        )
        FilterChip(
            selected = false,
            onClick = onSortByDuration,
            label = { Text("Duration") }
        )
    }
}
@Composable
private fun TrackItem(
    track: Track,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = track.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = track.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "${track.durationSec}s",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}


//@Composable
//fun HomeScreen(
//    onTrackSelected: (Track) -> Unit
//) {
//    val viewModel = remember {
//        getKoin().get<HomeViewModel>()
//    }
//
//    val uiState by viewModel.uiState.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.loadTracks()
//    }
//
//    when (uiState) {
//
//        is HomeUiState.Loading ->
//            CircularProgressIndicator()
//
//        is HomeUiState.Error ->
//            Text((uiState as HomeUiState.Error).message)
//
//        is HomeUiState.Success -> {
//            Column {
//
//                Row {
//                    Button(onClick = viewModel::sortByName) {
//                        Text("Sort A-Z")
//                    }
//                    Spacer(Modifier.width(8.dp))
//                    Button(onClick = viewModel::sortByDuration) {
//                        Text("Sort by Duration")
//                    }
//                }
//
//                LazyColumn {
//                    items((uiState as HomeUiState.Success<List<Track>>).data) { track ->
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(12.dp)
//                                .clickable {
//                                    onTrackSelected(track)
//                                }
//                        ) {
//                            Text(track.title, style = MaterialTheme.typography.titleMedium)
//                            Text(track.artist)
//                            Text("${track.durationSec}s")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

//
//@Composable
//fun HomeScreen(
//
//
//) {
//    val viewModel = remember {
//        getKoin().get<HomeViewModel>()
//    }
//
//
//    val uiState by viewModel.uiState.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.loadTracks()
//    }
//
//    when (uiState) {
//        is HomeUiState.Loading -> CircularProgressIndicator()
//
//        is HomeUiState.Error ->
//            Text((uiState as HomeUiState.Error).message)
//
//        is HomeUiState.Success -> {
//            Column {
//                Row {
//                    Button(onClick = { viewModel.sortByName() }) { Text("Sort A-Z") }
//                    Spacer(Modifier.width(8.dp))
//                    Button(onClick = { viewModel.sortByDuration() }){ Text("Sort by Duration") }
//                }
//
//                LazyColumn {
//                    items((uiState as HomeUiState.Success<List<Track>>).data) { track ->
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(12.dp)
//                        ) {
//                            Text(track.title, style = MaterialTheme.typography.titleMedium)
//                            Text(track.artist)
//                            Text("${track.durationSec}s")
//                            Button(onClick = { viewModel.play(track) }) {
//                                Text("Play")
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

