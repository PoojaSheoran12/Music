package com.user.music.data.local

import com.user.music.database.MusicDatabase

class DatabaseFactory(
    driverFactory: DatabaseDriverFactory
) {
    val database = MusicDatabase(
        driverFactory.createDriver()
    )
}
