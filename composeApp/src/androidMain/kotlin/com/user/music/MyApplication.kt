package com.user.music

import android.app.Application
import com.user.music.di.initKoinAndroid


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoinAndroid(this)
    }
}
