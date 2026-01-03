package com.user.music.navigation


import android.net.Uri


sealed class Route(val path: String) {
    object Home : Route("home")
    object Audio : Route("audio/{id}") {
        fun create(id: String) = "audio/$id"
    }

}
