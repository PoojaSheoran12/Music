package com.user.music.di

import io.ktor.http.ContentType
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(
             vararg platformModules: Module) {
    startKoin {
        modules(

            sharedModule,
            *platformModules

        )
    }
}
