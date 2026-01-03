package com.user.music.data.repository

import com.user.music.domain.Track
import com.user.music.data.mapper.toDomain
import com.user.music.data.network.ApiService

interface HomeRepository {
    suspend fun getTracks(): List<Track>
}

class HomeRepositoryImpl(
    private val api: ApiService
) : HomeRepository {

    override suspend fun getTracks(): List<Track> {
        return api.fetchTracks().map { it.toDomain() }
    }
}

