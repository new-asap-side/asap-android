package com.asap.aljyo.core.components.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@Immutable
data class MyRankingSheetState<T>(
    val show: Boolean,
    val myRanks: List<T>
)

@HiltViewModel
class MyRankingViewModel @Inject constructor() : NetworkViewModel() {
    override val prefix: String = "MyRanking"

    val fetchMyRankingFlow = flow {
        emit(1)
    }

    val myRankingState = fetchMyRankingFlow.map {

    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0
    )
}