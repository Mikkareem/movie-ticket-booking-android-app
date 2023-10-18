package com.techullurgy.movieticketbooking.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class SeatDetailsDTO(
    val totalRows: Int,
    val totalCols: Int,
    val seats: List<SeatWithStatusDTO>,
    val success: Boolean = true
)

@Serializable
data class SeatWithStatusDTO(
    val seat: SeatDTO,
    val status: SeatStatusDTO
)

@Serializable
data class SeatDTO(
    val id: Long,
    val seatRow: Int,
    val seatColumn: Int,
    val seatPrice: Double,
    val seatCategory: SeatCategoryDTO,
    val seatQualifier: String
)

enum class SeatCategoryDTO {
    FIRST_CLASS, SECOND_CLASS, BALCONY
}

enum class SeatStatusDTO {
    HOLD, AVAILABLE, NOT_AVAILABLE, BOOKED
}