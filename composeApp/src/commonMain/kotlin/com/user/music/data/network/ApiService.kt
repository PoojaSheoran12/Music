package com.user.music.data.network

import com.user.music.data.network.dto.TrackDto
import com.user.music.data.network.dto.TracksResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(
    private val client: HttpClient,
    private val clientId: String
) {

    suspend fun fetchTracks(offset: Int): List<TrackDto> {
        return client.get("https://api.jamendo.com/v3.0/tracks") {
            parameter("client_id", clientId)
            parameter("offset", offset)
        }.body<TracksResponseDto>().results
    }
}
