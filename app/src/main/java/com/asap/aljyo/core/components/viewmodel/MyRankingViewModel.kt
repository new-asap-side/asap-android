package com.asap.aljyo.core.components.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.remote.MyRanking
import com.asap.domain.usecase.group.MyRankingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class MyRankingSheetState(
    val show: Boolean,
    val myRanks: UiState<List<MyRanking>>
)

@HiltViewModel
class MyRankingViewModel @Inject constructor(
    private val myRankingUseCase: MyRankingUseCase
) : NetworkViewModel() {
    override val prefix: String = "MyRanking"

    private val _show = MutableStateFlow(false)
    private val _myRankingState = MutableStateFlow<UiState<List<MyRanking>>>(UiState.Loading)

    val sheetState = combine(_show, _myRankingState) { show, myRanking ->
        MyRankingSheetState(show, myRanking)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = MyRankingSheetState(false, UiState.Loading)
    )

    fun fetchMyRanking() {
        viewModelScope.launch(Dispatchers.Default) {
            myRankingUseCase().catch { e ->
                _myRankingState.emit(handleThrowable(e))
            }.collect { result ->
                _myRankingState.run {
                    result?.let { myRanking ->
                        emit(UiState.Success(myRanking))
                    } ?: emit(UiState.Error(-1))
                }
            }
        }
    }

    fun showSheet() {
        viewModelScope.launch {
            _show.emit(true)
        }
    }

    fun hideSheet() {
        viewModelScope.launch {
            _show.emit(false)
        }
    }
}