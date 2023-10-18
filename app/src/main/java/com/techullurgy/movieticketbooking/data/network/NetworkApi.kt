package com.techullurgy.movieticketbooking.data.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkApi {
    private val movieTicketBookingApi: IMovieTicketBookingApi by lazy {
        val httpClient = HttpClient(CIO) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("NETWORK_API", message)
                    }
                }
                level = LogLevel.INFO
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }

        MovieTicketBookingApi(httpClient)
    }

    fun getTicketBookingApi(): IMovieTicketBookingApi = movieTicketBookingApi
}