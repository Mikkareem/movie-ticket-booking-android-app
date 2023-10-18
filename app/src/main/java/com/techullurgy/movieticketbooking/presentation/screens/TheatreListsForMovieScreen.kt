package com.techullurgy.movieticketbooking.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.techullurgy.movieticketbooking.domain.Movie
import com.techullurgy.movieticketbooking.domain.Theatre
import com.techullurgy.movieticketbooking.presentation.layouts.LoadableContainer
import com.techullurgy.movieticketbooking.presentation.viewmodels.TheatresListViewModel

@Composable
fun TheatreListForMovieScreen(
    viewModel: TheatresListViewModel,
    navigateToShowsListScreen: (Long, Long) -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val theatresListScreenState by viewModel.theatresListScreenState.collectAsState()

    LoadableContainer(isLoading = isLoading) {
        AnimatedVisibility(visible = theatresListScreenState.error.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = theatresListScreenState.error)
            }
        }
        TheatresListForMovieScreen(
            movie = theatresListScreenState.movie,
            theatres = theatresListScreenState.theatres,
            navigateToShowsListScreen = navigateToShowsListScreen
        )
    }
}

@Composable
fun TheatresListForMovieScreen(
    movie: Movie,
    theatres: List<Theatre>,
    navigateToShowsListScreen: (Long, Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            Column {
                Text(text = movie.name)
                Text(text = movie.actors)
                Text(text = movie.censor.name)
                Text(text = movie.director)
                Text(text = movie.releaseDate)
            }
        }
        items(theatres) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigateToShowsListScreen(movie.id, it.theatreId) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = it.theatreName)
            }
        }
    }
}