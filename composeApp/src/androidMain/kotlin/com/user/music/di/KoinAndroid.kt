package com.user.music.di



import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoinAndroid(
    application: Application,
    vararg modules: Module
) {
    startKoin {
        androidContext(application) // 🔴 THIS IS WHAT YOU WERE MISSING
        modules(
            sharedModule,
            *modules
        )
    }
}
