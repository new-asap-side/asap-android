package com.asap.aljyo.core.components.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.ResultCard
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.usecase.ResultCardUseCase
import com.asap.domain.usecase.group.FetchLatestGroupUseCase
import com.asap.domain.usecase.group.FetchPopularGroupUseCase
import com.asap.domain.usecase.user.FetchFCMTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val resultCardUseCase: ResultCardUseCase,
    private val fetchPopularGroupUseCase: FetchPopularGroupUseCase,
    private val fetchLatestGroupUseCase: FetchLatestGroupUseCase,
    private val fetchFCMTokenUseCase: FetchFCMTokenUseCase,
) : ViewModel() {
    private val _cardState = MutableStateFlow<UiState<ResultCard?>>(UiState.Loading)
    val cardState get() = _cardState.asStateFlow()

    private val _popularGroupState = MutableStateFlow<UiState<List<AlarmGroup>?>>(UiState.Loading)
    val popularGroupState get() = _popularGroupState.asStateFlow()

    private val _latestGroupState = MutableStateFlow<UiState<List<AlarmGroup>?>>(UiState.Loading)
    val latestGroupState get() = _latestGroupState.asStateFlow()

    private val _scrollPositionMap = mutableMapOf(
        MAIN_TAB_SCROLL_KEY to Pair(0, 0),
        POPULAR_TAB_SCROLL_KEY to Pair(0, 0),
        LATEST_TAB_SCROLL_KEY to Pair(0, 0),
    )
    val scrollPositionMap get() = _scrollPositionMap

    private val _error = mutableStateOf(false)
    val error get() = _error.value

    init {
        fetchHomeData()
    }

    fun fetchHomeData() = viewModelScope.launch {
        _error.value = false
        _cardState.value = UiState.Loading
        _popularGroupState.value = UiState.Loading
        _latestGroupState.value = UiState.Loading

        delay(1000)
        resultCardUseCase.invoke()
            .catch { e ->
//                _cardState.value = handleThrowable(e)
                _cardState.value = UiState.Success(ResultCard())
            }
            .collect { resultCard -> _cardState.value = UiState.Success(resultCard) }

        fetchPopularGroupUseCase.invoke()
            .catch { e ->
                _popularGroupState.value = handleThrowable(e)
            }
            .collect { popularGroup ->
                _popularGroupState.value = UiState.Success(popularGroup)
            }

        fetchLatestGroupUseCase.invoke()
            .catch { e ->
                _latestGroupState.value = handleThrowable(e)
            }
            .collect { latestGroup ->
                _latestGroupState.value = UiState.Success(latestGroup)
            }

        fetchFCMTokenUseCase.invoke()

    }

    fun saveScrollPosition(key: String, index: Int, offset: Int) {
        _scrollPositionMap[key] = Pair(index, offset)
    }

    private fun handleThrowable(e: Throwable): UiState.Error {
        _error.value = true
        return when (e) {
            is HttpException -> UiState.Error(e.code())
            else -> UiState.Error(-1)
        }
    }

    companion object {
        const val MAIN_TAB_SCROLL_KEY = "main"
        const val POPULAR_TAB_SCROLL_KEY = "popular"
        const val LATEST_TAB_SCROLL_KEY = "latest"
    }
}