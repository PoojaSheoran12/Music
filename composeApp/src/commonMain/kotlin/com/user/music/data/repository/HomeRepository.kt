package com.user.music.data.repository

import com.user.music.domain.model.Track
import com.user.music.data.network.ApiService
import com.user.music.database.MusicDatabase
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.user.music.data.mapper.toDomain
import com.user.music.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PagingState() {
    var offset: Int = 0
    var isLoading = false
    var endReached = false
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
            .map { rows -> rows.map {it.toDomain()}
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

            paging.offset += response.size

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
