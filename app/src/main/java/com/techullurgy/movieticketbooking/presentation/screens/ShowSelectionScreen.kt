package com.techullurgy.movieticketbooking.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.techullurgy.movieticketbooking.navigation.NavigationConstant
import com.techullurgy.movieticketbooking.presentation.components.DateSelector
import com.techullurgy.movieticketbooking.presentation.layouts.LoadableContainer
import com.techullurgy.movieticketbooking.presentation.viewmodels.ShowSelectionScreenState
import com.techullurgy.movieticketbooking.presentation.viewmodels.ShowSelectionViewModel
import kotlinx.datetime.LocalDate

@Composable
fun ShowSelectionScreen(
    viewModel: ShowSelectionViewModel,
    navigateToBookSeatsScreen: (Long, Long, Long, String) -> Unit,
) {
    val navController = rememberNavController()


    val isLoading by viewModel.isLoading.collectAsState()
    val showSelectionScreenState by viewModel.showSelectionScreenStateFlow.collectAsState()
    val isScreenDetailsLoading by viewModel.isScreenDetailsLoading.collectAsState()

    LoadableContainer(isLoading = isLoading) {
        ShowSelectionScreen(
            showSelectionScreenState = showSelectionScreenState,
            onDateSelected = viewModel::onDateSelected,
            onShowSelected = viewModel::onShowSelected,
            isScreenDetailsLoading = isScreenDetailsLoading,
            modifier = Modifier.weight(1f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = { navController.popBackStack() }
            ) {
                Text(text = "Back")
            }

            AnimatedVisibility(visible = showSelectionScreenState.selectedShow != null) {
                Button(
                    onClick = {
                        val theatreId = navController.currentBackStackEntry?.arguments?.getLong(NavigationConstant.THEATRE_ID)!!
                        val screenId = showSelectionScreenState.selectedScreen ?: -1
                        val bookableShowId = showSelectionScreenState.selectedShow ?: -1
                        val orderDateStr = showSelectionScreenState.dateList.elementAt(showSelectionScreenState.selectedDateIndex).toString()

                        navigateToBookSeatsScreen(theatreId, screenId, bookableShowId, orderDateStr)
                    }
                ) {
                    Text(text = "Continue")
                }
            }
        }
    }
}

@Composable
internal fun ShowSelectionScreen(
    showSelectionScreenState: ShowSelectionScreenState,
    onDateSelected: (LocalDate) -> Unit,
    onShowSelected: (Long) -> Unit,
    isScreenDetailsLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = "Show Selection", fontWeight = FontWeight.ExtraBold, fontSize = 34.sp)
        DateSelector(
            dateList = showSelectionScreenState.dateList,
            selectedDateIndex = showSelectionScreenState.selectedDateIndex,
            onDateSelected = onDateSelected
        )
        LoadableContainer(isLoading = isScreenDetailsLoading) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(showSelectionScreenState.screenDetails) {
                    Row {
                        Column {
                            Text(text = it.screen.name)
                            LazyHorizontalGrid(
                                rows = GridCells.Fixed(3),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 200.dp)
                            ) {
                                items(it.availableShows) { show ->
                                    Button(onClick = { onShowSelected(show.bookableShowId) }) {
                                        Text(text = show.showTime.toString())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
