package com.techullurgy.movieticketbooking.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class TheatreDetailDTO(
    val theatre: TheatreDTO,
    val screenDetails: List<ScreenDetailDTO>
)
