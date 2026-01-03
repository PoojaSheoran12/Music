package com.user.music.audio



import android.content.*
import android.os.IBinder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

actual class AudioPlayer(
    private val context: Context
) {

    private var service: AudioService? = null
    private var pendingAction: (() -> Unit)? = null

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow(PlayerState())
    actual val state: StateFlow<PlayerState> = _state.asStateFlow()

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            service = (binder as AudioService.LocalBinder).getService()

            service!!.state
                .onEach { _state.value = it }
                .launchIn(scope)

            pendingAction?.invoke()
            pendingAction = null
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
        }
    }

    init {
        val intent = Intent(context, AudioService::class.java)
        context.startService(intent)
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    actual fun play(trackId: String, url: String) {
        if (service == null) {
            pendingAction = { play(trackId, url) }
            return
        }
        service?.play(trackId, url)
    }

    actual fun togglePlayPause() {
        service?.togglePlayPause()
    }

    actual fun seekTo(position: Long) {
        service?.seekTo(position)
    }

    actual fun release() {
        context.unbindService(connection)
        scope.cancel()
    }
}


//actual class AudioPlayer(
//    private val context: Context
//) {
//
//    private var service: AudioService? = null
//
//    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
//
//    private val _state = MutableStateFlow(PlayerState())
//    actual val state: StateFlow<PlayerState> = _state.asStateFlow()
//
//    // ✅ MUST BE ABOVE init
//    private val connection = object : ServiceConnection {
//        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
//            service = (binder as AudioService.LocalBinder).getService()
//
//            service!!.state
//                .onEach { _state.value = it }
//                .launchIn(scope)
//        }
//
//        override fun onServiceDisconnected(name: ComponentName?) {
//            service = null
//        }
//    }
//
//    // ✅ init runs AFTER connection is created
//    init {
//        bindService()
//    }
//
//    private fun bindService() {
//        val intent = Intent(context, AudioService::class.java)
//        context.startService(intent)
//        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
//    }
//
//    actual fun play(trackId: String, url: String) {
//        service?.play(trackId, url)
//    }
//
//    actual fun togglePlayPause() {
//        service?.togglePlayPause()
//    }
//
//    actual fun seekTo(position: Long) {
//        service?.seekTo(position)
//    }
//
//    actual fun release() {
//        service?.release()
//        context.unbindService(connection)
//        scope.cancel()
//    }
//}

//
//actual class AudioPlayer {
//
//    private lateinit var context: Context
//    private var mediaPlayer: MediaPlayer? = null
//
//    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
//
//    private val _state = MutableStateFlow(PlayerState())
//    actual val state: StateFlow<PlayerState> = _state.asStateFlow()
//
//    private var positionJob: Job? = null
//
//    fun init(context: Context) {
//        this.context = context
//    }
//
//    actual fun play(trackId: String, url: String) {
//        release()
//
//        mediaPlayer = MediaPlayer().apply {
//            setAudioAttributes(
//                AudioAttributes.Builder()
//                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                    .setUsage(AudioAttributes.USAGE_MEDIA)
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
//                    currentTrackId = trackId
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
//    actual fun togglePlayPause() {
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
//    actual fun seekTo(position: Long) {
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
//    actual fun release() {
//        stopPositionUpdates()
//        mediaPlayer?.release()
//        mediaPlayer = null
//    }
//}
