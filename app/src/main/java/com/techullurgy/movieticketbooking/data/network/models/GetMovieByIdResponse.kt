package com.techullurgy.movieticketbooking.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class GetMovieByIdResponse(
    val movie: MovieDTO,
    val success: Boolean
)
