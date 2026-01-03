package com.user.music.di

import com.user.music.presentation.audioPlayer.PlayerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val appScopeModule = module {
    single<CoroutineScope> {
        CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }
}
val playerCommonModule = module {
    single {
        PlayerViewModel(
            audioPlayer = get(),
            scope = get()
        )
    }
}
