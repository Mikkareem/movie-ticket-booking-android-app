package com.techullurgy.movieticketbooking.data.network

import com.techullurgy.movieticketbooking.data.network.models.MovieDTO
import com.techullurgy.movieticketbooking.data.network.models.SeatDetailsDTO
import com.techullurgy.movieticketbooking.data.network.models.TheatreDTO
import com.techullurgy.movieticketbooking.data.network.models.TheatreDetailDTO
import com.techullurgy.movieticketbooking.repositories.utils.ServiceResult
import kotlinx.datetime.LocalDate

interface IMovieTicketBookingApi {
    suspend fun getAllBookableMovies(): ServiceResult<List<MovieDTO>>
    suspend fun getBookableTheatresForMovie(movieId: Long): ServiceResult<List<TheatreDTO>>
    suspend fun getMovieDetail(movieId: Long): ServiceResult<MovieDTO>
    suspend fun getBookableDatesFromTheatreForMovie(
        movieId: Long,
        theatreId: Long
    ): ServiceResult<Set<LocalDate>>

    suspend fun getBookableScreensForMovie(
        movieId: Long,
        theatreId: Long,
        orderDate: LocalDate
    ): ServiceResult<TheatreDetailDTO>

    suspend fun getSeatDetailsForBookableShow(
        theatreId: Long,
        screenId: Long,
        bookableShowId: Long,
        orderDate: LocalDate
    ): ServiceResult<SeatDetailsDTO>
}