package com.user.music

import android.app.Application
import com.user.music.di.audioAndroidModule
import com.user.music.di.audioCommonModule
import com.user.music.di.initKoin
import com.user.music.di.initKoinAndroid


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoinAndroid(
            application = this,
            audioAndroidModule,
            audioCommonModule
        )
    }
}
