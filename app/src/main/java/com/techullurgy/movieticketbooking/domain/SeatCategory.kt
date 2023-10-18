package com.techullurgy.movieticketbooking.domain

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale

enum class SeatCategory {
    FIRST_CLASS, SECOND_CLASS, BALCONY;


    internal fun getCategoryStr(): String {
        return this.name.lowercase().let {
            if(it.contains('_')) {
                it.split('_').joinToString(" ").capitalize(Locale.current)
            } else {
                it.capitalize(Locale.current)
            }
        }
    }

    companion object {
        internal fun random(): SeatCategory = values().random()
    }
}