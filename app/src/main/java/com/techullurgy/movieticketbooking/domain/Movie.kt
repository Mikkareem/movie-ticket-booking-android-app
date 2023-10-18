package com.techullurgy.movieticketbooking.domain

import com.techullurgy.movieticketbooking.data.network.models.MovieDTO

data class Movie(
    val id: Long = -1,
    val name: String,
    val director: String,
    val actors: String,
    val releaseYear: Int,
    val censor: Censor,
    val originalLanguage: Language,
    val dubbedLanguage: Language,
    val releaseDate: String,
    val ticketsOpenDate: String,
    val posterUrl: String = "https://static.moviecrow.com/marquee/leo-update-month-begins-with-new-poster-release/221017_thumb_665.jpg"
)

fun MovieDTO.toMovie() = Movie(
    id = id,
    name = name,
    director = director,
    actors = actors,
    releaseYear = releaseYear,
    censor = censor,
    originalLanguage = originalLanguage,
    dubbedLanguage = dubbedLanguage ?: originalLanguage,
    releaseDate = releaseDate.toString(),
    ticketsOpenDate = ticketsOpenDate.toString()
)

val emptyMovie = Movie(
    id = -1,
    name = "",
    director = "",
    actors = "",
    releaseYear = 0,
    censor = Censor.U,
    originalLanguage = Language.TAMIL,
    dubbedLanguage = Language.TAMIL,
    releaseDate = "",
    ticketsOpenDate = "",
    posterUrl = ""
)