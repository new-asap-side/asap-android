package com.asap.aljyo.components.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.ResultCard
import com.asap.domain.usecase.ResultCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: ResultCardUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ResultCard?>>(UiState.Loading)
    val uiState get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            useCase.invoke()
                .catch { e ->
                    Log.e("HomeViewModel", "$e")
                    _uiState.value = UiState.Error(errorCode = "")
                }
                .collect { resultCard -> _uiState.value = UiState.Success(resultCard) }
        }
    }
}