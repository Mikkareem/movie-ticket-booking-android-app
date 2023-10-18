package com.techullurgy.movieticketbooking.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class TheatreDTO(
    val id: Long,
    val name: String,
    val address: String,
    val city: String,
    val state: String
)
