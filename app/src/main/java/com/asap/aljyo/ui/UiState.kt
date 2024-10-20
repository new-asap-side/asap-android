package com.asap.aljyo.ui

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val errorCode: String) : UiState<Nothing>()
}