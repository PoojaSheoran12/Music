package com.user.music.di

import com.user.music.audio.AudioPlayer
import com.user.music.audio.AudioViewModel
import com.user.music.ui.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//val playerModule = module {
//
//    single {
//        AudioPlayer().apply {
//            init(androidContext())
//        }
//    }
//
//    viewModel { AudioViewModel(get()) }
//}
val audioAndroidModule = module {
    single {
        AudioPlayer().apply {
            init(androidContext())
        }
    }
}
