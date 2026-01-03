package com.user.music.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

actual class AudioPlayer {

    private lateinit var context: Context
    private var mediaPlayer: MediaPlayer? = null

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow(PlayerState())
    actual val state: StateFlow<PlayerState> = _state.asStateFlow()

    private var positionJob: Job? = null

    fun init(context: Context) {
        this.context = context
    }

    actual fun play(trackId: String, url: String) {
        release()

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )

            setDataSource(url)

            setOnPreparedListener {
                start()
                _state.value = _state.value.copy(
                    isPlaying = true,
                    duration = it.duration.toLong(),
                    currentTrackId = trackId
                )
                startPositionUpdates()
            }

            setOnCompletionListener {
                _state.value = _state.value.copy(
                    isPlaying = false,
                    currentPosition = 0L
                )
                stopPositionUpdates()
            }

            setOnErrorListener { _, _, _ ->
                _state.value = _state.value.copy(
                    error = "Playback failed"
                )
                true
            }

            prepareAsync()
        }
    }

    actual fun togglePlayPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                stopPositionUpdates()
                _state.value = _state.value.copy(isPlaying = false)
            } else {
                it.start()
                startPositionUpdates()
                _state.value = _state.value.copy(isPlaying = true)
            }
        }
    }

    actual fun seekTo(position: Long) {
        mediaPlayer?.seekTo(position.toInt())
    }

    private fun startPositionUpdates() {
        positionJob?.cancel()
        positionJob = scope.launch {
            while (true) {
                mediaPlayer?.let {
                    _state.value = _state.value.copy(
                        currentPosition = it.currentPosition.toLong()
                    )
                }
                delay(500)
            }
        }
    }

    private fun stopPositionUpdates() {
        positionJob?.cancel()
        positionJob = null
    }

    actual fun release() {
        stopPositionUpdates()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
