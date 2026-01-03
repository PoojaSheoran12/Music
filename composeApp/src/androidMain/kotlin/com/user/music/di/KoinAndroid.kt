package com.user.music.di



import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

fun initKoinAndroid(
    application: Application
) {
    startKoin {
        androidContext(application)
        modules(commonModules + audioAndroidModule);

    }
}
