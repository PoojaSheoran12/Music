package com.user.music.config

import android.util.Log
import com.user.music.BuildConfig

actual fun provideClientId(): String {
    Log.d("id","${BuildConfig.JAMENDO_CLIENT_ID}")
    return BuildConfig.JAMENDO_CLIENT_ID
}
