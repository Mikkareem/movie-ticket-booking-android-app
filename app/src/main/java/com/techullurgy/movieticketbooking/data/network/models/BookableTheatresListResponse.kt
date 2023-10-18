package com.techullurgy.movieticketbooking.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class GetBookableTheatresListSuccessResponse(
    val movieId: Long,
    val theatres: List<TheatreDTO>,
    val success: Boolean
)