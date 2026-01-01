package com.user.music

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform