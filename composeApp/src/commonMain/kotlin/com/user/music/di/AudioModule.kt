package com.user.music.di

import com.user.music.audio.AudioViewModel
import org.koin.dsl.module

val audioCommonModule = module {
    single { AudioViewModel(get()) }
}
