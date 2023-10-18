package com.techullurgy.movieticketbooking.data.network.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class GetBookableShowDatesResponse(
    val dates: Set<LocalDate>,
    val success: Boolean
)
