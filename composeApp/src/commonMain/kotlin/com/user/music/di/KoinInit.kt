package com.user.music.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(vararg platformModules: Module) {
    startKoin {
        modules(
            sharedModule

        )
    }
}
