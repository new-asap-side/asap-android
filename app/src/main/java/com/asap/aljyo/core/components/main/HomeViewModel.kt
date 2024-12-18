package com.asap.aljyo.core.components.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.ResultCard
import com.asap.domain.usecase.ResultCardUseCase
import com.asap.domain.usecase.user.FetchFCMTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val resultCardUseCase: ResultCardUseCase,
    private val fetchFCMTokenUseCase: FetchFCMTokenUseCase,
) : ViewModel() {
    private val _cardState = MutableStateFlow<UiState<ResultCard?>>(UiState.Loading)
    val cardState get() = _cardState.asStateFlow()

    private val _scrollPositionMap = mutableMapOf(
        MAIN_TAB_SCROLL_KEY to Pair(0, 0),
        POPULAR_TAB_SCROLL_KEY to Pair(0, 0),
        LATEST_TAB_SCROLL_KEY to Pair(0, 0),
    )
    val scrollPositionMap get() = _scrollPositionMap

    init {
        viewModelScope.launch {
            // TODO remove
            delay(1000)
            resultCardUseCase.invoke()
                .catch { e ->
                    Log.e("HomeViewModel", "$e")
                    _cardState.value = UiState.Success(ResultCard())
                }
                .collect { resultCard -> _cardState.value = UiState.Success(resultCard) }

            fetchFCMTokenUseCase.invoke()
        }
    }

    fun saveScrollPosition(key: String, index: Int, offset: Int) {
        _scrollPositionMap[key] = Pair(index, offset)
    }

    companion object {
        const val MAIN_TAB_SCROLL_KEY = "main"
        const val POPULAR_TAB_SCROLL_KEY = "popular"
        const val LATEST_TAB_SCROLL_KEY = "latest"
    }
}