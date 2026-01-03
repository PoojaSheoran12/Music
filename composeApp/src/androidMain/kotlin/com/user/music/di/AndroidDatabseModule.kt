package com.user.music.di


import com.user.music.data.local.DatabaseDriverFactory
import com.user.music.data.local.DatabaseFactory
import com.user.music.database.MusicDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidDatabaseModule = module {
    single {
        DatabaseDriverFactory(androidContext())
    }

    single<MusicDatabase> {
        DatabaseFactory(get()).database
    }
}
