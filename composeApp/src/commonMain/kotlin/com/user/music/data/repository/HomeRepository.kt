package com.user.music.data.repository

import com.user.music.domain.Track
import com.user.music.data.network.ApiService
import com.user.music.database.MusicDatabase
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PagingState(
    val pageSize: Int = 10
) {
    var offset: Int = 0
    var isLoading = false
    var endReached = false
}

interface HomeRepository {
    fun observeTracks(): Flow<List<Track>>
    suspend fun loadNextPage()
    suspend fun refresh()
}

class HomeRepositoryImpl(
    private val api: ApiService,
    private val database: MusicDatabase
) : HomeRepository {

    private val paging = PagingState()
    private val queries = database.tracksQueries

    override fun observeTracks(): Flow<List<Track>> =
        queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { rows ->
                rows.map {
                    Track(
                        id = it.id,
                        title = it.title,
                        artist = it.artist,
                        durationSec = it.duration.toInt(),
                        imageUrl = it.imageUrl,
                        audioUrl = it.audioUrl
                    )
                }
            }


    override suspend fun loadNextPage() {
        if (paging.isLoading || paging.endReached) return
        paging.isLoading = true

        try {
            val response = api.fetchTracks(offset = paging.offset)

            if (response.isEmpty()) {
                paging.endReached = true
                return
            }

            database.transaction {
                response.forEach {
                    queries.insertTrack(
                        id = it.id,
                        title = it.name,
                        artist = it.artist_name,
                        duration = it.duration.toLong(),
                        imageUrl = it.image,
                        audioUrl = it.audio
                    )
                }
            }

            paging.offset += paging.pageSize
        } finally {
            paging.isLoading = false
        }
    }

    override suspend fun refresh() {
        paging.offset = 0
        paging.endReached = false

        database.transaction {
            queries.clearAll()
        }

        loadNextPage()
    }
}
