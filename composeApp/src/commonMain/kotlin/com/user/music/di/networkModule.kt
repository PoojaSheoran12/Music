package com.user.music.di

import com.user.music.config.provideClientId
import com.user.music.data.network.ApiClient
import com.user.music.data.network.ApiService
import org.koin.dsl.module

val networkModule =module(){

    single { ApiClient().client }
    single { ApiService(
        get(),
        clientId = provideClientId()
    )
    }
}