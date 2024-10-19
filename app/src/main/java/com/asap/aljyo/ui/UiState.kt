package com.asap.aljyo.ui

sealed interface UiState {
    data object Loading : UiState
    data object Success : UiState
    data class Error(val errorCode: String) : UiState
}