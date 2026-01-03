package com.user.music.di

import com.user.music.player.AudioPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val audioAndroidModule = module {
    single {
        AudioPlayer(
            context = androidContext()
        )
    }
}

