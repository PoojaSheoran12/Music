package com.user.music.di


import app.cash.sqldelight.db.SqlDriver // Import SqlDriver
import com.user.music.data.local.DatabaseDriverFactory
import com.user.music.data.local.DatabaseFactory
import com.user.music.database.MusicDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

//val androidDatabaseModule = module {
//
//    // This provides the platform-specific (Android) driver
//    single<SqlDriver> {
//        DatabaseDriverFactory(androidContext()).createDriver()
//    }
//
//    // This uses the provided driver to create a single instance of MusicDatabase
//    single<MusicDatabase> {
//        MusicDatabase(get()) // Pass the SqlDriver to the constructor
//    }
//}
val androidDatabaseModule = module {

    // Platform-specific driver factory
    single {
        DatabaseDriverFactory(androidContext())
    }

    // Use the existing `database` property
    single<MusicDatabase> {
        DatabaseFactory(get()).database
    }
}
