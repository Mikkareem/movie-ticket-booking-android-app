package com.techullurgy.movieticketbooking.domain

import com.techullurgy.movieticketbooking.data.network.models.ScreenDTO

data class Screen(
    val id: Long = -1,
    val name: String,
    val rows: Int,
    val cols: Int
)

fun ScreenDTO.toScreen(): Screen = Screen(id, name, rows, cols)
