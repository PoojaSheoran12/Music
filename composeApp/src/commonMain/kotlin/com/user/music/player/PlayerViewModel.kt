package com.user.music.player

import com.user.music.domain.model.Track
import com.user.music.player.PlayerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class PlayerViewModel(
    private val audioPlayer: AudioPlayer,
    private val scope: CoroutineScope
) {

    private val _playerState = MutableStateFlow(PlayerState())
    val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()

    private var playlist: List<Track> = emptyList()
    private var currentIndex: Int = -1

    init {

        audioPlayer.state
            .onEach { audioState ->
                _playerState.update {
                    it.copy(
                        isPlaying = audioState.isPlaying,
                        currentPosition = audioState.currentPosition,
                        duration = audioState.duration,
                        error = audioState.error
                    )
                }
            }
            .launchIn(scope)
    }

    fun setPlaylist(tracks: List<Track>) {
        playlist = tracks
    }

    fun playTrack(trackId: String) {
        if (_playerState.value.currentTrackId == trackId) return

        val index = playlist.indexOfFirst { it.id == trackId }
        if (index == -1) return

        currentIndex = index
        val track = playlist[index]

        _playerState.update {
            it.copy(
                currentTrackId = track.id,
                currentTrackTitle = track.title,
                currentTrackUrl = track.audioUrl,
                currentArtworkUrl = track.imageUrl,
                error = null
            )
        }

        audioPlayer.play(
            trackId = track.id,
            url = track.audioUrl
        )
    }

    fun playNext() {
        if (playlist.isEmpty() || currentIndex == -1) return

        currentIndex = (currentIndex + 1) % playlist.size
        playTrack(playlist[currentIndex].id)
    }

    fun playPrevious() {
        if (playlist.isEmpty() || currentIndex == -1) return

        currentIndex =
            if (currentIndex - 1 < 0) playlist.lastIndex
            else currentIndex - 1

        playTrack(playlist[currentIndex].id)
    }

    fun togglePlayPause() {
        audioPlayer.togglePlayPause()
    }

    fun seekTo(position: Long) {
        audioPlayer.seekTo(position)
    }
}
