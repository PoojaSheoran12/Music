package com.user.music.network.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class HeadersDto(
    val status: String,
    val code: Int,
    val error_message: String,
    val warnings: String,
    val results_count: Int,
    val next: String?
)
