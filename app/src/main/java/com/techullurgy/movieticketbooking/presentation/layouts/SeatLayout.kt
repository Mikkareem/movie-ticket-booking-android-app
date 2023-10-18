package com.techullurgy.movieticketbooking.presentation.layouts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.techullurgy.movieticketbooking.domain.Seat
import com.techullurgy.movieticketbooking.domain.SeatContent
import com.techullurgy.movieticketbooking.domain.SeatStatus
import com.techullurgy.movieticketbooking.domain.Vacuum
import com.techullurgy.movieticketbooking.presentation.components.seatView
import com.techullurgy.movieticketbooking.presentation.components.vacuumView

@Composable
internal fun SeatLayout(
    seats: List<SeatContent>,
    onSeatSelected: (Seat) -> Unit = {}
) {

    var height by remember { mutableStateOf(0.dp) }

    ZoomableContainer(
        modifier = Modifier.wrapContentSize()
    ) {

        val groupedSeats = seats.groupBy { it.row }

        val maxSeatInRow = groupedSeats.maxOf { it.value.size }

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .pointerInput(seats, height) {
                    detectTapGestures {
                        val seatSize = IntSize(size.width / maxSeatInRow, size.width / maxSeatInRow)
                        val row = (it.y / seatSize.height).toInt()
                        val col = (it.x / seatSize.width).toInt()
                        val currentSeatOccupancy: SeatContent? = groupedSeats[row]!!
                            .filter { seatContent -> seatContent.seatOccupancy is Seat }
                            .filter { seatContent -> (seatContent.seatOccupancy as Seat).point.col == col }
                            .firstOrNull { seatContent ->
                                val seat = seatContent.seatOccupancy as Seat
                                val result = seat.seatStatus == SeatStatus.AVAILABLE
                                        || seat.seatStatus == SeatStatus.SELECTED
                                println(result)
                                result
                            }

                        currentSeatOccupancy?.let { seatContent ->
                            val selectedSeat: Seat = seatContent.seatOccupancy as Seat
                            onSeatSelected(selectedSeat)
                        }
                    }
                }
        ) {

            val seatSize = Size(size.width / maxSeatInRow, size.width / maxSeatInRow)

            if(height == 0.dp) {
                height = (groupedSeats.size * seatSize.height).toDp()
            }

            repeat(groupedSeats.size) { row ->
                groupedSeats[row]?.forEachIndexed { index, value ->
                    when(value.seatOccupancy) {
                        is Seat -> seatView(
                            seat = value.seatOccupancy,
                            seatSize = seatSize,
                            at = Offset(
                                x = index * seatSize.width,
                                y = row * seatSize.height
                            )
                        )
                        is Vacuum -> vacuumView(vacuumSize = seatSize)
                    }
                }
            }
        }
    }
}