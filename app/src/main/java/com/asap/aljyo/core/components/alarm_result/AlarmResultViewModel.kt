package com.asap.aljyo.core.components.alarm_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.domain.usecase.group.FetchRankingNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AlarmResultViewModel @Inject constructor(
    private val fetchRankingNumberUseCase: FetchRankingNumberUseCase
): ViewModel() {
    private val _rankingNumber = MutableStateFlow<UiState<String>>(UiState.Loading)
    val rankingNumber get() = _rankingNumber.asStateFlow()

    fun fetchRankingNumber(groupId: Int) = viewModelScope.launch {
        fetchRankingNumberUseCase(groupId = groupId).catch { e ->
            val errorCode = when (e) {
                is HttpException -> e.code()
                else -> -1
            }

            _rankingNumber.value = UiState.Error(errorCode)
        }.collect { response ->
            val number = (response?.rankNumber ?: "-").toString()
            _rankingNumber.value = UiState.Success(number)
        }
    }
}