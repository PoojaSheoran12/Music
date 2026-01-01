package com.user.music.repository

import com.user.music.domain.Track
import com.user.music.mapper.toDomain
import com.user.music.network.ApiService

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

