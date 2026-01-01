package com.user.music.ui

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.user.music.domain.Track
import kotlinx.coroutines.flow.StateFlow
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun HomeScreen(


) {
    val viewModel = remember {
        getKoin().get<HomeViewModel>()
    }


    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTracks()
    }

    when (uiState) {
        is HomeUiState.Loading -> CircularProgressIndicator()

        is HomeUiState.Error ->
            Text((uiState as HomeUiState.Error).message)

        is HomeUiState.Success -> {
            Column {
                Row {
                    Button(onClick = { viewModel.sortByName() }) { Text("Sort A-Z") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { viewModel.sortByDuration() }){ Text("Sort by Duration") }
                }

                LazyColumn {
                    items((uiState as HomeUiState.Success<List<Track>>).data) { track ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Text(track.title, style = MaterialTheme.typography.titleMedium)
                            Text(track.artist)
                            Text("${track.durationSec}s")
                            Button(onClick = { viewModel.play(track) }) {
                                Text("Play")
                            }
                        }
                    }
                }
            }
        }
    }
}
