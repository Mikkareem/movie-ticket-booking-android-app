package com.techullurgy.movieticketbooking.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techullurgy.movieticketbooking.domain.ScreenDetail
import com.techullurgy.movieticketbooking.navigation.NavigationConstant
import com.techullurgy.movieticketbooking.repositories.RepositoryStore
import com.techullurgy.movieticketbooking.repositories.utils.ServiceResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate

class ShowSelectionViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _showSelectionScreenStateFlow = MutableStateFlow(ShowSelectionScreenState())
    val showSelectionScreenStateFlow: StateFlow<ShowSelectionScreenState> get() = _showSelectionScreenStateFlow.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _isScreenDetailsLoading = MutableStateFlow(false)
    val isScreenDetailsLoading: StateFlow<Boolean> get() = _isScreenDetailsLoading.asStateFlow()

    init {
        _isLoading.update { true }
        viewModelScope.launch {
            val movieId = savedStateHandle.get<Long>(NavigationConstant.MOVIE_ID)!!
            val theatreId = savedStateHandle.get<Long>(NavigationConstant.THEATRE_ID)!!

            val datesResult = RepositoryStore.getTicketBookingRepository().getBookableDatesFromTheatreForMovie(movieId, theatreId)

            if(datesResult is ServiceResult.Failure) {
                _showSelectionScreenStateFlow.update {
                    it.copy(
                        error = datesResult.error
                    )
                }
                _isLoading.update { false }
                return@launch
            }

            val dates = (datesResult as ServiceResult.Success).data

            if(dates.isEmpty()) {
                _showSelectionScreenStateFlow.update {
                    it.copy(
                        error = "No Available Dates"
                    )
                }
                _isLoading.update { false }
                return@launch
            }

            val screenDetailsResult = loadScreenDetails(movieId, theatreId, dates.elementAt(0))

            if(screenDetailsResult is ServiceResult.Failure) {
                _showSelectionScreenStateFlow.update {
                    it.copy(
                        error = screenDetailsResult.error
                    )
                }
                _isLoading.update { false }
                return@launch
            }

            _showSelectionScreenStateFlow.update {
                it.copy(
                    error = "",
                    dateList = dates
                )
            }
            _isLoading.update { false }
        }
    }

    private suspend fun loadScreenDetails(
        movieId: Long,
        theatreId: Long,
        orderDate: LocalDate
    ): ServiceResult<List<ScreenDetail>> {
        val screenDetailsResult = RepositoryStore.getTicketBookingRepository()
            .getBookableScreenDetails(movieId, theatreId, orderDate)
        if(screenDetailsResult is ServiceResult.Failure) {
            return screenDetailsResult
        }

        _showSelectionScreenStateFlow.update {
            it.copy(
                screenDetails = (screenDetailsResult as ServiceResult.Success).data
            )
        }
        return screenDetailsResult
    }

    fun onDateSelected(date: LocalDate) {
        val movieId = savedStateHandle.get<Long>(NavigationConstant.MOVIE_ID)!!
        val theatreId = savedStateHandle.get<Long>(NavigationConstant.THEATRE_ID)!!

        _isScreenDetailsLoading.update { true }
        viewModelScope.launch {
            val screenDetailsResult = loadScreenDetails(movieId, theatreId, date)
            if(screenDetailsResult is ServiceResult.Failure) {
                _showSelectionScreenStateFlow.update {
                    it.copy(
                        error = screenDetailsResult.error,
                        screenDetails = emptyList()
                    )
                }
            }
            _showSelectionScreenStateFlow.update {
                it.copy(
                    selectedDateIndex = it.dateList.indexOf(date)
                )
            }
            delay(2000)
            _isScreenDetailsLoading.update { false }
        }
    }

    fun onShowSelected(showId: Long) {
        _showSelectionScreenStateFlow.update {
            it.copy(
                selectedShow = showId,
                selectedScreen = it.screenDetails.find { screen ->
                    screen.availableShows.any { show -> show.bookableShowId == showId }
                }!!.screen.id
            )
        }
    }

    fun getTheatreId(): Long {
        return savedStateHandle.get<Long>(NavigationConstant.THEATRE_ID)!!
    }
}

data class ShowSelectionScreenState(
    val error: String = "",
    val dateList: Set<LocalDate> = emptySet(),
    val selectedDateIndex: Int = 0,
    val screenDetails: List<ScreenDetail> = emptyList(),
    val selectedShow: Long? = null,
    val selectedScreen: Long? = null
)

fun main() {
    runBlocking {
        val result = RepositoryStore.getTicketBookingRepository().getBookableScreenDetails(83, 2, LocalDate(2023, 10, 18))
        println(result)
    }
}