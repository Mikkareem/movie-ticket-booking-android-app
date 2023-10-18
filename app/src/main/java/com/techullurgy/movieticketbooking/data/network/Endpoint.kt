package com.techullurgy.movieticketbooking.data.network

sealed class Endpoint(private val endpoint: String) {
    object GetBookableMovies: Endpoint("bookable_movies")
    class GetMovieById(movieId: Long): Endpoint("find/MOVIE/$movieId")
    class GetMovieByName(name: String): Endpoint("search/MOVIE/$name")
    class GetTheatreByName(name: String): Endpoint("search/THEATRE/$name")
    class GetBookableTheatreListForMovie(movieId: Long): Endpoint("theatre_list/$movieId")

    class GetBookableShowListFromTheatreForMovie(
        movieId: Long,
        theatreId: Long,
        date: String
    ): Endpoint("show_list/$movieId/$theatreId/$date")

    class GetBookableShowDatesFromTheatreForMovie(
        movieId: Long,
        theatreId: Long
    ): Endpoint("show_dates/$movieId/$theatreId")

    class GetCurrentSeatDetails(
        theatreId: Long,
        screenId: Long,
        showId: Long,
        date: String
    ): Endpoint("seat_details/$theatreId/$screenId/$showId/$date")

    class BookTicket(
        theatreId: Long,
        screenId: Long,
        showId: Long,
        movieId: Long,
        date: String
    ):  Endpoint("book_ticket?theatre=$theatreId&screen=$screenId&show=$showId&movie=$movieId&date=$date")

    class GetMyTickets(customerId: Long): Endpoint("my_tickets/$customerId")

    class GetTicketDetails(ticketId: Long): Endpoint("ticket/$ticketId")

    companion object {
        private const val BASE_URL = "http://192.168.225.154:8080"//"http://127.0.0.1:8080"
    }

    fun getPath(): String = "$BASE_URL/$endpoint"
}
