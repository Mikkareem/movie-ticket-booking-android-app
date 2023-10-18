package com.techullurgy.movieticketbooking.domain

import kotlin.random.Random
import kotlin.reflect.KClass

sealed interface SeatOccupancy {
    companion object {
        internal fun randomSeatAt(row: Int, col: Int): SeatOccupancy {
            return if(Random.nextFloat() > 0.7f) {
                Vacuum(
                    point = Point(row = row, col = col)
                )
            } else {
                Seat(
                    seatId = -1,
                    point = Point(row = row, col = col),
                    seatStatus = SeatStatus.random(),
                    seatPrice = Random.nextDouble(120.0, 300.0),
                    seatCategory = SeatCategory.random()
                )
            }
        }

        internal fun randomSeatAt(row: Int, col: Int, occupancy: String): SeatOccupancy {
            return if(occupancy == "V") {
                Vacuum(
                    point = Point(row = row, col = col)
                )
            } else {
                Seat(
                    seatId = -1,
                    point = Point(row = row, col = col),
                    seatStatus = SeatStatus.random(),
                    seatPrice = Random.nextDouble(120.0, 300.0),
                    seatCategory = SeatCategory.random()
                )
            }
        }

        internal fun randomSeatAt(row: Int, col: Int, occupancy: KClass<out SeatOccupancy>): SeatOccupancy {
            return if(occupancy == Seat::class) {
                Seat(
                    seatId = -1,
                    point = Point(row = row, col = col),
                    seatStatus = SeatStatus.random(),
                    seatPrice = Random.nextDouble(120.0, 300.0),
                    seatCategory = SeatCategory.random()
                )
            } else {
                Vacuum(
                    point = Point(row = row, col = col)
                )
            }
        }
    }
}

data class Vacuum(val point: Point): SeatOccupancy

data class Seat(
    val seatId: Long,
    val point: Point,
    val seatStatus: SeatStatus,
    val seatPrice: Double,
    val seatCategory: SeatCategory
): SeatOccupancy {
    companion object {}
}

internal fun firstClassSeat(point: Point, seatStatus: SeatStatus = SeatStatus.AVAILABLE): SeatOccupancy
        = Seat(
            seatId = -1,
            point = point,
            seatStatus = seatStatus,
            seatCategory = SeatCategory.FIRST_CLASS,
            seatPrice = 230.0
        )

internal fun secondClassSeat(point: Point, seatStatus: SeatStatus = SeatStatus.AVAILABLE): SeatOccupancy
        = Seat(
            seatId = -1,
            point = point,
            seatStatus = seatStatus,
            seatCategory = SeatCategory.SECOND_CLASS,
            seatPrice = 260.0
        )

internal fun balconySeat(point: Point, seatStatus: SeatStatus = SeatStatus.AVAILABLE): SeatOccupancy
        = Seat(
            seatId = -1,
            point = point,
            seatStatus = seatStatus,
            seatCategory = SeatCategory.BALCONY,
            seatPrice = 310.0
        )

internal fun vacuumSeat(point: Point): SeatOccupancy = Vacuum(point)