package com.techullurgy.movieticketbooking.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class GetBookableShowDetailsResponse(
    val data: TheatreDetailDTO,
    val success: Boolean
)
