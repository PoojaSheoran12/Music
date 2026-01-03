package com.user.music.navigation


import android.net.Uri


sealed class Route(val path: String) {
    object Home : Route("home")
    object Audio : Route("audio/{id}/{url}") {
        fun create(id: String, url: String) =
            "audio/$id/${Uri.encode(url)}"
    }
}
