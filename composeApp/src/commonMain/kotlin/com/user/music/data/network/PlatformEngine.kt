package com.user.music.data.network

import io.ktor.client.engine.HttpClientEngine

expect fun provideHttpClientEngine(): HttpClientEngine
