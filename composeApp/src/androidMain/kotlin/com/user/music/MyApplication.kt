package com.user.music

import android.app.Application
import com.user.music.di.initKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}
