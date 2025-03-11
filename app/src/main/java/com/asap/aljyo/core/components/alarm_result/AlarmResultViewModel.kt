package com.asap.aljyo.core.components.alarm_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.domain.usecase.group.FetchRankingNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RankingState(
    val rank: String,
    val score: Int = 2000 - (if (rank == "-") 0 else rank.toInt() * 200)
) {
    companion object {
        fun initial() = RankingState(rank = "-")
    }
}

@HiltViewModel
class AlarmResultViewModel @Inject constructor(
    private val fetchRankingNumberUseCase: FetchRankingNumberUseCase
): ViewModel() {
    private val _rankingState = MutableStateFlow(RankingState.initial())
    val rankState get() = _rankingState.asStateFlow()

    fun fetchRankingNumber(groupId: Int) {
        viewModelScope.launch {
            fetchRankingNumberUseCase(groupId = groupId).catch {
                _rankingState.emit(RankingState.initial())
            }.collect { response ->
                response?.let {
                    _rankingState.emit(RankingState(rank = it.rankNumber.toString()))
                }
            }
        }
    }
}