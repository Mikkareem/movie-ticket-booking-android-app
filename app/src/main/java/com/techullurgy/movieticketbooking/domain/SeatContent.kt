package com.techullurgy.movieticketbooking.domain

import com.techullurgy.movieticketbooking.data.network.models.SeatDetailsDTO
import com.techullurgy.movieticketbooking.data.network.models.SeatWithStatusDTO

data class SeatContent(
    val row: Int,
    val column: Int,
    val seatOccupancy: SeatOccupancy
)


val seatContents = mutableListOf<SeatContent>().apply {
    repeat(30) { row ->
        repeat(35) { col ->

            val occupancy: SeatOccupancy = if(row == 4 || row == 21 || row == 22) {
                vacuumSeat(Point(row, col))
            } else if(col == 4 || col == 32) {
                vacuumSeat(Point(row, col))
            } else {
                if(row < 4) {
                    firstClassSeat(Point(row, col))
                } else if(row > 20) {
                    balconySeat(Point(row, col))
                } else {
                    secondClassSeat(Point(row, col))
                }
            }

            add(
                SeatContent(
                    row = row,
                    column = col,
                    seatOccupancy = occupancy
                )
            )
        }
    }
}.toList()

fun SeatDetailsDTO.toSeatContents(): List<SeatContent> {
    val result = mutableListOf<SeatContent>()

    for(row in 0 until totalRows) {
        for(col in 0 until totalCols) {
            val seatWithStatus = seats.find { it.seat.seatRow == row && it.seat.seatColumn == col }

            seatWithStatus?.let {
                result.add(
                    SeatContent(
                        row = row,
                        column = col,
                        seatOccupancy = it.toSeat()
                    )
                )
            } ?: result.add(
                    SeatContent(
                        row = row,
                        column = col,
                        seatOccupancy = Vacuum(point = Point(row, col))
                    )
                )
        }
    }

    return result.toList()
}

private fun SeatWithStatusDTO.toSeat(): Seat = Seat(
        seatId = seat.id,
        point = Point(seat.seatRow, seat.seatColumn),
        seatStatus = SeatStatus.valueOf(status.name),
        seatPrice = seat.seatPrice,
        seatCategory = SeatCategory.valueOf(seat.seatCategory.name)
    )
