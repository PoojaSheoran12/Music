package com.user.music.network

import com.user.music.network.dto.TrackDto
import com.user.music.network.dto.TracksResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(
    private val client: HttpClient
) {

    suspend fun fetchTracks(): List<TrackDto> {
        return client.get("https://api.jamendo.com/v3.0/tracks") {
            parameter("client_id", "37a13b71")
            parameter("limit", 20)
        }.body<TracksResponseDto>().results
    }
}
