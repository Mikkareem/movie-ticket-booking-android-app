package com.techullurgy.movieticketbooking.domain

import com.techullurgy.movieticketbooking.data.network.models.ScreenDetailDTO
import kotlinx.datetime.LocalTime

data class ScreenDetail(
    val screen: Screen,
    val availableShows: List<ShowDetail>
)

data class ShowDetail(
    val bookableShowId: Long,
    val showTime: LocalTime,
    val showStatus: ShowStatus
)

enum class ShowStatus {
    HOUSE_FULL, AVAILABLE, CLOSED, ALMOST_FULL
}

fun ScreenDetailDTO.toScreenDetail(): ScreenDetail = ScreenDetail(
    screen = this.screen.toScreen(),
    availableShows = this.bookableShowFullDetails.map {
        ShowDetail(it.bookableShowId, it.showTime, ShowStatus.AVAILABLE)
    }
)