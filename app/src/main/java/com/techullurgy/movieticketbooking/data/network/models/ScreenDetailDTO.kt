package com.techullurgy.movieticketbooking.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ScreenDetailDTO(
    val screen: ScreenDTO,
    val movieId: Long,
    val bookableShowFullDetails: List<BookableShowDetail>
)
