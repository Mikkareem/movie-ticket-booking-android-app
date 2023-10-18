package com.techullurgy.movieticketbooking.data.network

import com.techullurgy.movieticketbooking.data.network.models.GetBookableMoviesSuccessResponse
import com.techullurgy.movieticketbooking.data.network.models.GetBookableShowDatesResponse
import com.techullurgy.movieticketbooking.data.network.models.GetBookableShowDetailsResponse
import com.techullurgy.movieticketbooking.data.network.models.GetBookableTheatresListSuccessResponse
import com.techullurgy.movieticketbooking.data.network.models.GetMovieByIdResponse
import com.techullurgy.movieticketbooking.data.network.models.MovieDTO
import com.techullurgy.movieticketbooking.data.network.models.SeatDetailsDTO
import com.techullurgy.movieticketbooking.data.network.models.TheatreDTO
import com.techullurgy.movieticketbooking.data.network.models.TheatreDetailDTO
import com.techullurgy.movieticketbooking.data.network.models.ValidationResponse
import com.techullurgy.movieticketbooking.repositories.utils.ServiceResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

class MovieTicketBookingApi(
    private val client: HttpClient
): IMovieTicketBookingApi {
    override suspend fun getAllBookableMovies(): ServiceResult<List<MovieDTO>> = withContext(Dispatchers.IO) {
        try {
            val response = client.get(Endpoint.GetBookableMovies.getPath())
            when (response.status) {
                HttpStatusCode.OK -> {
                    val data = response.body<GetBookableMoviesSuccessResponse>().movies
                    println(data)
                    ServiceResult.Success(data)
                }
                HttpStatusCode.BadRequest -> {
                    val message = response.body<ValidationResponse>().message
                    ServiceResult.Failure(message)
                }
                else -> {
                    ServiceResult.Failure("Unknown Error")
                }
            }
        } catch (e: Exception) {
            ServiceResult.Failure(e.localizedMessage!!)
        }
    }

    override suspend fun getBookableTheatresForMovie(movieId: Long): ServiceResult<List<TheatreDTO>> = withContext(Dispatchers.IO) {
        try {
            val response = client.get(Endpoint.GetBookableTheatreListForMovie(movieId).getPath())
            when(response.status) {
                HttpStatusCode.OK -> {
                    val data = response.body<GetBookableTheatresListSuccessResponse>().theatres
                    ServiceResult.Success(data)
                }
                HttpStatusCode.BadRequest -> {
                    val message = response.body<ValidationResponse>().message
                    ServiceResult.Failure(message)
                }
                else -> {
                    ServiceResult.Failure("Unknown Error")
                }
            }
        } catch(e: Exception) {
            ServiceResult.Failure(e.localizedMessage!!)
        }
    }

    override suspend fun getMovieDetail(movieId: Long): ServiceResult<MovieDTO> = withContext(Dispatchers.IO) {
        try {
            val response = client.get(Endpoint.GetMovieById(movieId).getPath())
            when(response.status) {
                HttpStatusCode.OK -> {
                    val data = response.body<GetMovieByIdResponse>().movie
                    ServiceResult.Success(data)
                }
                HttpStatusCode.BadRequest -> {
                    val message = response.body<ValidationResponse>().message
                    ServiceResult.Failure(message)
                }
                else -> {
                    ServiceResult.Failure("Unknown Error")
                }
            }
        } catch(e: Exception) {
            ServiceResult.Failure(e.localizedMessage!!)
        }
    }

    override suspend fun getBookableDatesFromTheatreForMovie(
        movieId: Long,
        theatreId: Long
    ): ServiceResult<Set<LocalDate>>  = withContext(Dispatchers.IO) {
        try {
            val response = client.get(Endpoint.GetBookableShowDatesFromTheatreForMovie(movieId, theatreId).getPath())
            when(response.status) {
                HttpStatusCode.OK -> {
                    val data = response.body<GetBookableShowDatesResponse>().dates
                    ServiceResult.Success(data)
                }
                HttpStatusCode.BadRequest -> {
                    val message = response.body<ValidationResponse>().message
                    ServiceResult.Failure(message)
                }
                else -> {
                    ServiceResult.Failure("Unknown Error")
                }
            }
        } catch(e: Exception) {
            ServiceResult.Failure(e.localizedMessage!!)
        }
    }

    override suspend fun getBookableScreensForMovie(
        movieId: Long,
        theatreId: Long,
        orderDate: LocalDate
    ): ServiceResult<TheatreDetailDTO> = withContext(Dispatchers.IO) {
        try {
            val date = orderDate.toNetworkStr()
            val response = client.get(Endpoint.GetBookableShowListFromTheatreForMovie(movieId, theatreId, date).getPath())
            when(response.status) {
                HttpStatusCode.OK -> {
                    val data = response.body<GetBookableShowDetailsResponse>().data
                    ServiceResult.Success(data)
                }
                HttpStatusCode.BadRequest -> {
                    val message = response.body<ValidationResponse>().message
                    ServiceResult.Failure(message)
                }
                else -> {
                    ServiceResult.Failure("Unknown Error")
                }
            }
        } catch(e: Exception) {
            ServiceResult.Failure(e.localizedMessage!!)
        }
    }

    override suspend fun getSeatDetailsForBookableShow(
        theatreId: Long,
        screenId: Long,
        bookableShowId: Long,
        orderDate: LocalDate
    ): ServiceResult<SeatDetailsDTO> = withContext(Dispatchers.IO) {
        try {
            val date = orderDate.toNetworkStr()
            val response = client.get(Endpoint.GetCurrentSeatDetails(theatreId, screenId, bookableShowId, date).getPath())
            when(response.status) {
                HttpStatusCode.OK -> {
                    val data = response.body<SeatDetailsDTO>()
                    ServiceResult.Success(data)
                }
                HttpStatusCode.BadRequest -> {
                    val message = response.body<ValidationResponse>().message
                    ServiceResult.Failure(message)
                }
                else -> {
                    ServiceResult.Failure("Unknown Error")
                }
            }
        } catch(e: Exception) {
            ServiceResult.Failure(e.localizedMessage!!)
        }
    }

    private fun LocalDate.toNetworkStr(): String {
        val day = dayOfMonth.toString().padStart(2, '0')
        val monthNumber = monthNumber.toString().padStart(2, '0')
        return "$day-$monthNumber-$year"
    }
}

fun main() {
    val client = HttpClient(CIO) {
        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json()
        }
    }

    val api = MovieTicketBookingApi(client)

    runBlocking {
        val result = api.getBookableScreensForMovie(83, 2, LocalDate(2023, 10, 19))
        if(result is ServiceResult.Failure) {

        }
    }
}