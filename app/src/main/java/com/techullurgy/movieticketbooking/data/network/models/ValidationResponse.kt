package com.techullurgy.movieticketbooking.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ValidationResponse(
    val message: String,
    val success: Boolean
)
