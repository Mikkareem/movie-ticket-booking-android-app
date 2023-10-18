package com.techullurgy.movieticketbooking.repositories.utils

sealed class ServiceResult<out T> {
    data class Success<T>(val data: T): ServiceResult<T>()
    data class Failure(val error: String): ServiceResult<Nothing>()
}
