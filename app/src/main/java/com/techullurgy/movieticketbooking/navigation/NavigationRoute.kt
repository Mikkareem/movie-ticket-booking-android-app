package com.techullurgy.movieticketbooking.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavigationRoute(private val route: String) {
    object SearchScreenRoute: NavigationRoute("home")

    object TheatresListForMovieRoute: NavigationRoute("theatres_list_for_movie/{${NavigationConstant.MOVIE_ID}}") {
        fun getNamedArguments(): List<NamedNavArgument> {
            return listOf(
                navArgument(NavigationConstant.MOVIE_ID) {
                    type = NavType.LongType
                }
            )
        }
        fun navigateWith(movieId: Long): String = "theatres_list_for_movie/$movieId"
    }

    object ShowsListFromTheatreForMovieRoute: NavigationRoute("shows_list_from_theatre_for_movie/{${NavigationConstant.MOVIE_ID}}/{theatre_id}") {
        fun getNamedArguments(): List<NamedNavArgument> {
            return listOf(
                navArgument(NavigationConstant.MOVIE_ID) {
                    type = NavType.LongType
                },
                navArgument(NavigationConstant.THEATRE_ID) {
                    type = NavType.LongType
                }
            )
        }
        fun navigateWith(movieId: Long, theatreId: Long): String = "shows_list_from_theatre_for_movie/$movieId/$theatreId"
    }

    object BookSeatsForShowRoute: NavigationRoute("seat_details/{${NavigationConstant.THEATRE_ID}}/{${NavigationConstant.SCREEN_ID}}/{${NavigationConstant.SHOW_ID}}/{${NavigationConstant.ORDER_DATE}}") {
        fun getNamedArguments(): List<NamedNavArgument> {
            return listOf(
                navArgument(NavigationConstant.THEATRE_ID) {
                    type = NavType.LongType
                },
                navArgument(NavigationConstant.SCREEN_ID) {
                    type = NavType.LongType
                },
                navArgument(NavigationConstant.SHOW_ID) {
                    type = NavType.LongType
                },
                navArgument(NavigationConstant.ORDER_DATE) {
                    type = NavType.StringType
                },
            )
        }
        fun navigateWith(theatreId: Long, screenId: Long, showId: Long, orderDate: String): String = "seat_details/$theatreId/$screenId/$showId/$orderDate"
    }

    fun getPath(): String = route
}