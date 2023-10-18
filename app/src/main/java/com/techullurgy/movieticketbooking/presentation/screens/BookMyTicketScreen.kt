package com.techullurgy.movieticketbooking.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techullurgy.movieticketbooking.domain.Seat
import com.techullurgy.movieticketbooking.domain.SeatStatus
import com.techullurgy.movieticketbooking.presentation.components.PriceList
import com.techullurgy.movieticketbooking.presentation.components.SeatSelectionButtonSection
import com.techullurgy.movieticketbooking.presentation.components.SeatSelectionDetails
import com.techullurgy.movieticketbooking.presentation.layouts.SeatLayout
import com.techullurgy.movieticketbooking.presentation.viewmodels.BookMyTicketScreenState
import com.techullurgy.movieticketbooking.presentation.viewmodels.BookMyTicketViewModel

@Composable
fun BookMyTicketScreen(
    viewModel: BookMyTicketViewModel
) {
    val bookMyTicketScreenState by viewModel.bookMyTicketScreenState.collectAsState()

    BookMyTicketScreen(
        bookMyTicketScreenState = bookMyTicketScreenState,
        onSeatSelected = viewModel::addSelectedSeat,
        onSeatUnselected = viewModel::removeSelectedSeat
    )
}

@Composable
internal fun BookMyTicketScreen(
    bookMyTicketScreenState: BookMyTicketScreenState,
    onSeatSelected: (Seat) -> Unit = {},
    onSeatUnselected: (Seat) -> Unit = {},
) {
    val totalSeatsSelected = bookMyTicketScreenState.selectedSeats.size

//    val selectedMovie = bookMyTicketScreenState.selectedMovie
//    val selectedTheatre = bookMyTicketScreenState.selectedTheatre
//    val selectedDate = bookMyTicketScreenState.selectedDate
//    val selectedShowTime = bookMyTicketScreenState.selectedShowTime

    val totalAmount = bookMyTicketScreenState.totalAmount

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
//                color = Color(0xffa6966f)
                color = Color(0xff11a4f6)
            )
            .padding(16.dp)
    ) {

        Text(text = "Book My Ticket", fontSize = 42.sp, fontWeight = FontWeight.ExtraBold)

//        AnnotatedText(leftText = "Movie", rightText = selectedMovie)
//        AnnotatedText(leftText = "Theatre", rightText = selectedTheatre)
//        AnnotatedText(leftText = "Date", rightText = selectedDate)
//        AnnotatedText(leftText = "Show Time", rightText = selectedShowTime)

        Spacer(modifier = Modifier.height(16.dp))
        SeatLayout(
            seats = bookMyTicketScreenState.seats,
            onSeatSelected = {
                if(it.seatStatus == SeatStatus.AVAILABLE) {
                    onSeatSelected(it)
                } else {
                    onSeatUnselected(it)
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        PriceList(
            categories = bookMyTicketScreenState.categories
        )

        SeatSelectionDetails(
            totalSeatsSelected = totalSeatsSelected,
            totalAmount = totalAmount,
            selectedSeats = bookMyTicketScreenState.selectedSeats
        )

        Spacer(modifier = Modifier.height(8.dp))

        SeatSelectionButtonSection(
            totalSeatsSelected = totalSeatsSelected
        )
    }
}
