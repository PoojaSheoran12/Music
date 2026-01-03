package com.user.music.player

import androidx.lifecycle.ViewModel
import com.user.music.domain.Track
import kotlinx.coroutines.flow.StateFlow
class AudioViewModel(
    private val audioPlayer: AudioPlayer
)  {

    val playerState: StateFlow<PlayerState> = audioPlayer.state

    private var playlist: List<Track> = emptyList()
    private var currentIndex: Int = -1

    /* ---------- PLAYLIST ---------- */

    fun setPlaylist(tracks: List<Track>) {
        playlist = tracks
    }

    /* ---------- PLAY TRACK (FROM LIST) ---------- */

    fun playTrack(trackId: String, url: String) {

        // 🔑 Prevent restart of same track
        if (playerState.value.currentTrackId == trackId) return

        // 🔑 Set currentIndex correctly
        val index = playlist.indexOfFirst { it.id == trackId }
        if (index != -1) {
            currentIndex = index
        }

        audioPlayer.play(trackId, url)
    }

    /* ---------- NEXT ---------- */

    fun playNext() {
        if (playlist.isEmpty()) return
        if (currentIndex == -1) return

        currentIndex = (currentIndex + 1) % playlist.size
        val track = playlist[currentIndex]
        audioPlayer.play(track.id, track.audioUrl)
    }

    /* ---------- PREVIOUS ---------- */

    fun playPrevious() {
        if (playlist.isEmpty()) return
        if (currentIndex == -1) return

        currentIndex =
            if (currentIndex - 1 < 0) playlist.lastIndex
            else currentIndex - 1

        val track = playlist[currentIndex]
        audioPlayer.play(track.id, track.audioUrl)
    }

    /* ---------- CONTROLS ---------- */

    fun togglePlayPause() {
        audioPlayer.togglePlayPause()
    }

    fun seekTo(position: Long) {
        audioPlayer.seekTo(position)
    }

     fun onCleared() {
        audioPlayer.release()
    }
}


