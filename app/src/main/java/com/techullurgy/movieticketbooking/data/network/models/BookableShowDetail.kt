package com.techullurgy.movieticketbooking.data.network.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookableShowDetail(
    @SerialName("showId")
    val bookableShowId: Long,
    val showDate: LocalDate,
    val showTime: LocalTime
)
