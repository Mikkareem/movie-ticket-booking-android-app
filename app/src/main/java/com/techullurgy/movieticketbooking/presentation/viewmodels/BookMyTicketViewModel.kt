package com.techullurgy.movieticketbooking.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techullurgy.movieticketbooking.domain.Seat
import com.techullurgy.movieticketbooking.domain.SeatContent
import com.techullurgy.movieticketbooking.domain.SeatStatus
import com.techullurgy.movieticketbooking.domain.seatContents
import com.techullurgy.movieticketbooking.navigation.NavigationConstant
import com.techullurgy.movieticketbooking.repositories.MovieTicketBookingRepository
import com.techullurgy.movieticketbooking.repositories.RepositoryStore
import com.techullurgy.movieticketbooking.repositories.utils.ServiceResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class BookMyTicketViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _bookMyTicketScreenStateFlow = MutableStateFlow(BookMyTicketScreenState())
    val bookMyTicketScreenState: StateFlow<BookMyTicketScreenState> get() = _bookMyTicketScreenStateFlow.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val ticketBookingRepository: MovieTicketBookingRepository = RepositoryStore.getTicketBookingRepository()

    init {
        _isLoading.update { true }

        viewModelScope.launch {
            val theatreId = savedStateHandle.get<Long>(NavigationConstant.THEATRE_ID)!!
            val screenId = savedStateHandle.get<Long>(NavigationConstant.SCREEN_ID)!!
            val bookableShowId = savedStateHandle.get<Long>(NavigationConstant.SHOW_ID)!!
            val orderDate = savedStateHandle.get<String>(NavigationConstant.ORDER_DATE)!!.let { LocalDate.parse(it) }

            val seatContentsResult = ticketBookingRepository.getBookableSeatDetails(theatreId, screenId, bookableShowId, orderDate)
            if(seatContentsResult is ServiceResult.Failure) {
                _bookMyTicketScreenStateFlow.update {
                    it.copy(
                        error = seatContentsResult.error
                    )
                }
                _isLoading.update { false }
                return@launch
            }

            val seatContents = (seatContentsResult as ServiceResult.Success).data
            _bookMyTicketScreenStateFlow.update {
                it.copy(
                    error = "",
                    seats = seatContents
                )
            }
        }.invokeOnCompletion {
            _isLoading.update { false }
        }
    }

    fun addSelectedSeat(seat: Seat) {
        val totalAmount = _bookMyTicketScreenStateFlow.value.totalAmount + seat.seatPrice

        _bookMyTicketScreenStateFlow.update {
            it.copy(
                selectedSeats = it.selectedSeats.toMutableList().apply {
                    add(seat)
                }.toList(),
                totalAmount = totalAmount,
                seats = it.seats.toMutableList().apply {
                    val indexToRemove = indexOfFirst { seatContent -> seatContent.seatOccupancy == seat }
                    val foundSeatContent = get(indexToRemove)
                    removeAt(indexToRemove)
                    val foundedSeat = foundSeatContent.seatOccupancy as Seat
                    val newSeat = foundedSeat.copy(seatStatus = SeatStatus.SELECTED)
                    val newSeatContent = foundSeatContent.copy(seatOccupancy = newSeat)
                    add(indexToRemove, newSeatContent)
                }
            )
        }
    }

    fun removeSelectedSeat(seat: Seat) {
        val reducedAmount = _bookMyTicketScreenStateFlow.value.totalAmount - seat.seatPrice

        _bookMyTicketScreenStateFlow.update {
            it.copy(
                selectedSeats = it.selectedSeats.toMutableList().apply {
                    val index = indexOfFirst { currentSeat -> seat.point == currentSeat.point }
                    removeAt(index)
                }.toList(),
                totalAmount = reducedAmount,
                seats = it.seats.toMutableList().apply {
                    val indexToRemove = indexOfFirst { seatContent -> seatContent.seatOccupancy == seat }
                    val foundSeatContent = get(indexToRemove)
                    removeAt(indexToRemove)
                    val foundedSeat = foundSeatContent.seatOccupancy as Seat
                    val newSeat = foundedSeat.copy(seatStatus = SeatStatus.AVAILABLE)
                    val newSeatContent = foundSeatContent.copy(seatOccupancy = newSeat)
                    add(indexToRemove, newSeatContent)
                }
            )
        }
    }
}

data class BookMyTicketScreenState(
    val error: String = "",
    val seats: List<SeatContent> = seatContents,
    val selectedSeats: List<Seat> = emptyList(),
    val totalAmount: Double = 0.0,
    val selectedMovie: String = "",
    val selectedTheatre: String = "",
    val selectedDate: String = "",
    val selectedShowTime: String = "",
    val categories: List<Seat> = emptyList()
)