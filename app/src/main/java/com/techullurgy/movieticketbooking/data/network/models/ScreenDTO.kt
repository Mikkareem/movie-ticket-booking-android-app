package com.techullurgy.movieticketbooking.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ScreenDTO(
    val id: Long,
    val name: String,
    val rows: Int,
    val cols: Int
)
