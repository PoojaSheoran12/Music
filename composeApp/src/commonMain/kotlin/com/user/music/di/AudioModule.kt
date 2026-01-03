package com.user.music.di

import com.user.music.player.AudioViewModel
import org.koin.dsl.module

val audioCommonModule = module {
    single { AudioViewModel(get()) }
}
