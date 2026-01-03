package com.user.music.player



import android.content.*
import android.os.IBinder
import com.user.music.player.PlayerState
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

