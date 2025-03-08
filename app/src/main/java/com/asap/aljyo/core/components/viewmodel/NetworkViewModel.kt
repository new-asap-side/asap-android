package com.asap.aljyo.core.components.viewmodel

import androidx.lifecycle.ViewModel
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.UiState
import retrofit2.HttpException


abstract class NetworkViewModel: ViewModel() {
    protected abstract val prefix: String
    protected val tag get() = "${prefix}ViewModel"

    protected fun handleThrowable(e: Throwable): UiState.Error {
        return when (e) {
            is HttpException -> UiState.Error(e.code())
            else -> UiState.Error(-1)
        }
    }

    protected fun handleRequestThrowable(e: Throwable): RequestState.Error {
        return when(e) {
            is HttpException -> RequestState.Error(e.code())
            else -> RequestState.Error(-1)
        }
    }
}