package com.user.music.domain.repository

import com.user.music.domain.model.Track
import kotlinx.coroutines.flow.Flow


interface HomeRepository {
    fun observeTracks(): Flow<List<Track>>
    suspend fun loadNextPage()
    suspend fun refresh()
}
