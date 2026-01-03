package com.user.music.audio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.user.music.ui.HomeViewModel
import org.koin.mp.KoinPlatform.getKoin


@Composable
fun AudioScreen(
    playerState: PlayerState,
    onPlayPause: () -> Unit,
    onSeek: (Long) -> Unit
) {
    PlayerControls(
        state = playerState,
        onPlayPause = onPlayPause,
        onSeek = onSeek
    )
}

//@Composable
//fun AudioScreen(
//    trackId: String,
//    audioUrl: String
//) {
//    val viewModel = remember {
//        getKoin().get<AudioViewModel>()
//    }
//
//    val playerState by viewModel.playerState.collectAsState()
//
//    LaunchedEffect(trackId) {
//        viewModel.playTrack(trackId, audioUrl)
//    }
//
//    PlayerControls(
//        state = playerState,
//        onPlayPause = viewModel::togglePlayPause,
//        onSeek = viewModel::seekTo
//    )
//}


//@Composable
//fun AudioScreen(
////    viewModel: AudioViewModel = koinViewModel()
//) {
//    val viewModel = remember {
//        getKoin().get<AudioViewModel>()
//    }
//
//    val playerState by viewModel.playerState.collectAsState()
//
//    PlayerControls(
//        state = playerState,
//        onPlayPause = viewModel::togglePlayPause,
//        onSeek = viewModel::seekTo
//    )
//}
