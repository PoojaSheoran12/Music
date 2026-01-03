package com.user.music.audio

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class AudioService : Service() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private var mediaPlayer: MediaPlayer? = null
    private var positionJob: Job? = null

    private val _state = MutableStateFlow(PlayerState())
    val state: StateFlow<PlayerState> = _state.asStateFlow()

    inner class LocalBinder : Binder() {

        fun getService(): AudioService = this@AudioService
    }

    private val binder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder  {
        Log.d("AudioService", "SERVICE BOUND")
        return  binder;
    }

    fun play(trackId: String, url: String) {
        release()

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )

            setDataSource(url)

            setOnPreparedListener {
                start()
                _state.value = _state.value.copy(
                    isPlaying = true,
                    duration = it.duration.toLong(),
                    currentTrackId = trackId,
                    currentTrackUrl = url
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
                _state.value = _state.value.copy(error = "Playback failed")
                true
            }

            prepareAsync()
        }
    }

    fun togglePlayPause() {
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

    fun seekTo(position: Long) {
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

    fun release() {
        stopPositionUpdates()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        release()
        super.onDestroy()
    }
}



//class AudioService : Service() {
//
//    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
//
//    private var mediaPlayer: MediaPlayer? = null
//    private var positionJob: Job? = null
//
//    private val _state = MutableStateFlow(PlayerState())
//    val state: StateFlow<PlayerState> = _state.asStateFlow()
//
//    // ✅ REQUIRED: named Binder
//    inner class LocalBinder : Binder() {
//        fun getService(): AudioService = this@AudioService
//    }
//
//    private val binder = LocalBinder()
//
//    override fun onBind(intent: Intent?): IBinder = binder
//
//    fun play(trackId: String, url: String) {
//        release()
//
//        mediaPlayer = MediaPlayer().apply {
//            setAudioAttributes(
//                AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_MEDIA)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                    .build()
//            )
//
//            setDataSource(url)
//
//            setOnPreparedListener {
//                start()
//                _state.value = _state.value.copy(
//                    isPlaying = true,
//                    duration = it.duration.toLong(),
//                    currentTrackId = trackId,
//                    currentTrackUrl = url // ✅ IMPORTANT
//                )
//                startPositionUpdates()
//            }
//
//            setOnCompletionListener {
//                _state.value = _state.value.copy(
//                    isPlaying = false,
//                    currentPosition = 0L
//                )
//                stopPositionUpdates()
//            }
//
//            setOnErrorListener { _, _, _ ->
//                _state.value = _state.value.copy(
//                    error = "Playback failed"
//                )
//                true
//            }
//
//            prepareAsync()
//        }
//    }
//
//
//    fun togglePlayPause() {
//        mediaPlayer?.let {
//            if (it.isPlaying) {
//                it.pause()
//                stopPositionUpdates()
//                _state.value = _state.value.copy(isPlaying = false)
//            } else {
//                it.start()
//                startPositionUpdates()
//                _state.value = _state.value.copy(isPlaying = true)
//            }
//        }
//    }
//
//    fun seekTo(position: Long) {
//        mediaPlayer?.seekTo(position.toInt())
//    }
//
//    private fun startPositionUpdates() {
//        positionJob?.cancel()
//        positionJob = scope.launch {
//            while (true) {
//                mediaPlayer?.let {
//                    _state.value = _state.value.copy(
//                        currentPosition = it.currentPosition.toLong()
//                    )
//                }
//                delay(500)
//            }
//        }
//    }
//
//    private fun stopPositionUpdates() {
//        positionJob?.cancel()
//        positionJob = null
//    }
//
//    fun release() {
//        stopPositionUpdates()
//        mediaPlayer?.release()
//        mediaPlayer = null
//    }
//
//    override fun onDestroy() {
//        release()
//        super.onDestroy()
//    }
//}

//
//class AudioService : Service() {
//
//    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
//
//    private var mediaPlayer: MediaPlayer? = null
//    private var positionJob: Job? = null
//
//    private val _state = MutableStateFlow(PlayerState())
//    val state: StateFlow<PlayerState> = _state.asStateFlow()
//
//    override fun onBind(intent: Intent?): IBinder = binder
//
//    private val binder = object : Binder() {
//        fun getService(): AudioService = this@AudioService
//    }
//
//    fun play(trackId: String, url: String) {
//        release()
//
//        mediaPlayer = MediaPlayer().apply {
//            setAudioAttributes(
//                AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_MEDIA)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                    .build()
//            )
//            setDataSource(url)
//            setOnPreparedListener {
//                start()
//                _state.value = _state.value.copy(
//                    isPlaying = true,
//                    duration = it.duration.toLong(),
//                    currentTrackId = trackId
//                )
//                startPositionUpdates()
//                startForegroundNotification()
//            }
//            setOnCompletionListener {
//                _state.value = _state.value.copy(isPlaying = false)
//                stopPositionUpdates()
//                stopForeground(false)
//            }
//            prepareAsync()
//        }
//    }
//
//    fun togglePlayPause() {
//        mediaPlayer?.let {
//            if (it.isPlaying) {
//                it.pause()
//                stopPositionUpdates()
//                _state.value = _state.value.copy(isPlaying = false)
//            } else {
//                it.start()
//                startPositionUpdates()
//                _state.value = _state.value.copy(isPlaying = true)
//            }
//        }
//    }
//
//    fun seekTo(position: Long) {
//        mediaPlayer?.seekTo(position.toInt())
//    }
//
//    private fun startPositionUpdates() {
//        positionJob?.cancel()
//        positionJob = scope.launch {
//            while (true) {
//                mediaPlayer?.let {
//                    _state.value = _state.value.copy(
//                        currentPosition = it.currentPosition.toLong()
//                    )
//                }
//                delay(500)
//            }
//        }
//    }
//
//    private fun stopPositionUpdates() {
//        positionJob?.cancel()
//        positionJob = null
//    }
//
//    private fun startForegroundNotification() {
//        // Minimal foreground notification (mandatory)
//    }
//
//    fun release() {
//        stopPositionUpdates()
//        mediaPlayer?.release()
//        mediaPlayer = null
//        stopForeground(true)
//    }
//
//    override fun onDestroy() {
//        release()
//        super.onDestroy()
//    }
//}
