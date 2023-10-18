package com.techullurgy.movieticketbooking.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.techullurgy.movieticketbooking.presentation.components.BookableMovies
import com.techullurgy.movieticketbooking.presentation.components.SearchSection
import com.techullurgy.movieticketbooking.presentation.layouts.LoadableContainer
import com.techullurgy.movieticketbooking.presentation.viewmodels.SearchScreenViewModel


@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel,
    navigateToTheatresListScreen: (Long) -> Unit
) {
    val searchScreenState by viewModel.searchScreenState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LoadableContainer(isLoading = isLoading) {
        SearchSection()
        BookableMovies(
            movies = searchScreenState.bookableMovies,
            navigateToTheatresListScreen = navigateToTheatresListScreen
        )
    }
}