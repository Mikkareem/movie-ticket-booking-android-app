package com.techullurgy.movieticketbooking.domain

import com.techullurgy.movieticketbooking.data.network.models.TheatreDTO

data class Theatre(
    val theatreId: Long,
    val theatreName: String,
    val address: String,
    val city: String,
    val state: String
)

fun TheatreDTO.toTheatre(): Theatre = Theatre(
    theatreId = id,
    theatreName = name,
    address = address,
    city = city,
    state = state
)