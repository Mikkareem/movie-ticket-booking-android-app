package com.techullurgy.movieticketbooking.domain


enum class SeatStatus {
    AVAILABLE, NOT_AVAILABLE, BOOKED, SELECTED;

    companion object {
        internal fun random(): SeatStatus = values().random()
    }
}