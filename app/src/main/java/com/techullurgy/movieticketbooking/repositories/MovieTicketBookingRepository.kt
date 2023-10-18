package com.techullurgy.movieticketbooking.repositories

import com.techullurgy.movieticketbooking.data.network.IMovieTicketBookingApi
import com.techullurgy.movieticketbooking.domain.Movie
import com.techullurgy.movieticketbooking.domain.ScreenDetail
import com.techullurgy.movieticketbooking.domain.SeatContent
import com.techullurgy.movieticketbooking.domain.Theatre
import com.techullurgy.movieticketbooking.domain.toMovie
import com.techullurgy.movieticketbooking.domain.toScreenDetail
import com.techullurgy.movieticketbooking.domain.toSeatContents
import com.techullurgy.movieticketbooking.domain.toTheatre
import com.techullurgy.movieticketbooking.repositories.utils.ServiceResult
import kotlinx.datetime.LocalDate

class MovieTicketBookingRepository(
    private val movieTicketBookingApi: IMovieTicketBookingApi
) {
    suspend fun getBookableMovies(): ServiceResult<List<Movie>> {
        val result = movieTicketBookingApi.getAllBookableMovies()
        if(result is ServiceResult.Failure) {
            return result
        }

        val data = (result as ServiceResult.Success).data
        val movies = data.map { it.toMovie() }
        return ServiceResult.Success(movies)
    }

    suspend fun getBookableTheatresForMovie(movieId: Long): ServiceResult<List<Theatre>> {
        val result = movieTicketBookingApi.getBookableTheatresForMovie(movieId)
        if(result is ServiceResult.Failure) return result

        val data = (result as ServiceResult.Success).data
        val theatres = data.map { it.toTheatre() }
        return ServiceResult.Success(theatres)
    }

    suspend fun getMovieDetail(movieId: Long): ServiceResult<Movie> {
        val result = movieTicketBookingApi.getMovieDetail(movieId)
        if(result is ServiceResult.Failure) return result

        val data = (result as ServiceResult.Success).data
        val movie = data.toMovie()
        return ServiceResult.Success(movie)
    }

    suspend fun getBookableDatesFromTheatreForMovie(movieId: Long, theatreId: Long): ServiceResult<Set<LocalDate>> {
        return movieTicketBookingApi.getBookableDatesFromTheatreForMovie(movieId, theatreId)
    }

    suspend fun getBookableScreenDetails(movieId: Long, theatreId: Long, orderDate: LocalDate): ServiceResult<List<ScreenDetail>> {
        val result = movieTicketBookingApi.getBookableScreensForMovie(movieId, theatreId, orderDate)
        if(result is ServiceResult.Failure) return result

        val screenDetails = (result as ServiceResult.Success).data.screenDetails.map { it.toScreenDetail() }
        return ServiceResult.Success(screenDetails)
    }

    suspend fun getBookableSeatDetails(theatreId: Long, screenId: Long, bookableShowId: Long, orderDate: LocalDate): ServiceResult<List<SeatContent>> {
        val result = movieTicketBookingApi.getSeatDetailsForBookableShow(theatreId, screenId, bookableShowId, orderDate)
        if(result is ServiceResult.Failure) return result

        val seatContents = (result as ServiceResult.Success).data.toSeatContents()
        return ServiceResult.Success(seatContents)
    }
}