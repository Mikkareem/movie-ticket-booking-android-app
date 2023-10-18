package com.techullurgy.movieticketbooking.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techullurgy.movieticketbooking.domain.Movie
import com.techullurgy.movieticketbooking.domain.Theatre
import com.techullurgy.movieticketbooking.domain.emptyMovie
import com.techullurgy.movieticketbooking.navigation.NavigationConstant
import com.techullurgy.movieticketbooking.repositories.RepositoryStore
import com.techullurgy.movieticketbooking.repositories.utils.ServiceResult
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TheatresListViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _theatresListScreenState = MutableStateFlow(TheatresListScreenState())
    val theatresListScreenState: StateFlow<TheatresListScreenState> get() = _theatresListScreenState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    init {
        _isLoading.update { true }
        viewModelScope.launch {
            val movieId = savedStateHandle.get<Long>(NavigationConstant.MOVIE_ID)!!
            val bookableTheatresForMovieJob = async { RepositoryStore.getTicketBookingRepository().getBookableTheatresForMovie(movieId) }
            val movieDetailJob = async { RepositoryStore.getTicketBookingRepository().getMovieDetail(movieId) }

            val bookableTheatres = bookableTheatresForMovieJob.await()
            val movieDetail = movieDetailJob.await()

            if(bookableTheatres is ServiceResult.Failure) {
                _theatresListScreenState.update {
                    it.copy(error = bookableTheatres.error)
                }
                _isLoading.update { false }
                return@launch
            }

            if(movieDetail is ServiceResult.Failure) {
                _theatresListScreenState.update {
                    it.copy(error = movieDetail.error)
                }
                _isLoading.update { false }
                return@launch
            }

            val movie = (movieDetail as ServiceResult.Success).data
            val theatres = (bookableTheatres as ServiceResult.Success).data

            _theatresListScreenState.update {
                it.copy(
                    error = "",
                    movie = movie,
                    theatres = theatres
                )
            }
            _isLoading.update { false }
        }
    }
}

data class TheatresListScreenState(
    val error: String = "",
    val movie: Movie = emptyMovie,
    val theatres: List<Theatre> = emptyList()
)