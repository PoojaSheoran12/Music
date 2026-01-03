package com.user.music.data.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual fun provideHttpClientEngine(): HttpClientEngine {
    return OkHttp.create()
}