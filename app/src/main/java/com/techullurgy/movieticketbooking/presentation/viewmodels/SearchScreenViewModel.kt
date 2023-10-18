package com.techullurgy.movieticketbooking.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techullurgy.movieticketbooking.domain.Movie
import com.techullurgy.movieticketbooking.repositories.RepositoryStore
import com.techullurgy.movieticketbooking.repositories.utils.ServiceResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchScreenViewModel: ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _searchScreenState = MutableStateFlow(SearchScreenState())
    val searchScreenState: StateFlow<SearchScreenState> get() = _searchScreenState.asStateFlow()

    init {
        _isLoading.update { true }
        viewModelScope.launch {
            val bookableMoviesResult = RepositoryStore.getTicketBookingRepository().getBookableMovies()
            if(bookableMoviesResult is ServiceResult.Failure) {
                _searchScreenState.update {
                    it.copy(
                        error = bookableMoviesResult.error
                    )
                }
            } else {
                _searchScreenState.update {
                    it.copy(
                        bookableMovies = (bookableMoviesResult as ServiceResult.Success).data,
                        error = ""
                    )
                }
            }
            _isLoading.update { false }
        }
    }
}

data class SearchScreenState(
    val bookableMovies: List<Movie> = emptyList(),
    val error: String = ""
)