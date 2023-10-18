package com.techullurgy.movieticketbooking.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.techullurgy.movieticketbooking.presentation.screens.BookMyTicketScreen
import com.techullurgy.movieticketbooking.presentation.screens.SearchScreen
import com.techullurgy.movieticketbooking.presentation.screens.ShowSelectionScreen
import com.techullurgy.movieticketbooking.presentation.screens.TheatreListForMovieScreen
import com.techullurgy.movieticketbooking.presentation.viewmodels.BookMyTicketViewModel
import com.techullurgy.movieticketbooking.presentation.viewmodels.SearchScreenViewModel
import com.techullurgy.movieticketbooking.presentation.viewmodels.ShowSelectionViewModel
import com.techullurgy.movieticketbooking.presentation.viewmodels.TheatresListViewModel

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoute.SearchScreenRoute.getPath()) {
        composable(route = NavigationRoute.SearchScreenRoute.getPath()) {
            val searchViewModel = viewModel<SearchScreenViewModel>()

            val navigateToTheatresListScreen: (Long) -> Unit = {
                navController.navigate(
                    NavigationRoute.TheatresListForMovieRoute.navigateWith(it)
                )
            }

            SearchScreen(
                viewModel = searchViewModel,
                navigateToTheatresListScreen = navigateToTheatresListScreen
            )
        }

        composable(
            route = NavigationRoute.TheatresListForMovieRoute.getPath(),
            arguments = NavigationRoute.TheatresListForMovieRoute.getNamedArguments()
        ) {

            val theatresListViewModel = viewModel<TheatresListViewModel>()

            val navigateToShowsListScreen: (Long, Long) -> Unit = { movieId, theatreId ->
                navController.navigate(
                    NavigationRoute.ShowsListFromTheatreForMovieRoute.navigateWith(movieId, theatreId)
                )
            }

            TheatreListForMovieScreen(
                viewModel = theatresListViewModel,
                navigateToShowsListScreen = navigateToShowsListScreen
            )
        }

        composable(
            route = NavigationRoute.ShowsListFromTheatreForMovieRoute.getPath(),
            arguments = NavigationRoute.ShowsListFromTheatreForMovieRoute.getNamedArguments()
        ) {
            val navigateToBookSeatsScreen: (Long, Long, Long, String) -> Unit = { theatreId, screenId, showId, orderDate ->
                navController.navigate(
                    NavigationRoute.BookSeatsForShowRoute.navigateWith(theatreId, screenId, showId, orderDate)
                )
            }

            val viewModel = viewModel<ShowSelectionViewModel>()

            ShowSelectionScreen(
                viewModel = viewModel,
                navigateToBookSeatsScreen = navigateToBookSeatsScreen,
                onBackButtonPressed = navController::popBackStack
            )
        }

        composable(
            route = NavigationRoute.BookSeatsForShowRoute.getPath(),
            arguments = NavigationRoute.BookSeatsForShowRoute.getNamedArguments()
        ) {
            val viewModel = viewModel<BookMyTicketViewModel>()

            BookMyTicketScreen(viewModel = viewModel)
        }
    }
}