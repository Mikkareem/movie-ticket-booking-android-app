package com.techullurgy.movieticketbooking.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class GetBookableMoviesSuccessResponse(
    val movies: List<MovieDTO>,
    val success: Boolean = true
)