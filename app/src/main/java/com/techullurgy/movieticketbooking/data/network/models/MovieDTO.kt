package com.techullurgy.movieticketbooking.data.network.models

import com.techullurgy.movieticketbooking.domain.Censor
import com.techullurgy.movieticketbooking.domain.Language
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MovieDTO(
    val id: Long = -1,
    val name: String,
    val director: String,
    val actors: String,
    val releaseYear: Int,
    val censor: Censor,
    val originalLanguage: Language,
    val dubbedLanguage: Language? = null,
    val releaseDate: LocalDate,
    val ticketsOpenDate: LocalDateTime
)
