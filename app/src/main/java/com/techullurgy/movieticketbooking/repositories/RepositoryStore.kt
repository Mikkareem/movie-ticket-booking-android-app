package com.techullurgy.movieticketbooking.repositories

import com.techullurgy.movieticketbooking.data.network.NetworkApi

object RepositoryStore {
    private val movieTicketBookingRepository: MovieTicketBookingRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        MovieTicketBookingRepository(NetworkApi.getTicketBookingApi())
    }

    fun getTicketBookingRepository(): MovieTicketBookingRepository = movieTicketBookingRepository
}