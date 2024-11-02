package com.asap.aljyo.ui

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val errorCode: String? = null) : UiState<Nothing>()
}

sealed class RequestState<out T> {
    data object Initial : RequestState<Nothing>()
    data object Loading : RequestState<Nothing>()
    data class Success<out T>(val data: T) : RequestState<T>()
    data class Error(val errorCode: String? = null) : RequestState<Nothing>()
}