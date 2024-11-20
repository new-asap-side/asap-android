package com.asap.aljyo.components.group_ranking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.remote.GroupRanking
import com.asap.domain.usecase.group.FetchGroupRankingUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class GroupRankingViewModel @AssistedInject constructor(
    fetchGroupRankingUseCase: FetchGroupRankingUseCase,
    @Assisted private val groupId: Int
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<GroupRanking>>>(UiState.Loading)
    val state get() = _state

    init {
        Log.d(TAG, "viewModel init block groupId: $groupId")
        viewModelScope.launch {
            delay(500)
            fetchGroupRankingUseCase.invoke(groupId).catch { e ->
                // TODO 에러 처리
                // _state.value = UiState.Error("")
                Log.e(TAG, "$e")
                _state.value = UiState.Success((1..8).map { GroupRanking.dummy() })
            }.collect { rankingList ->
                _state.value = UiState.Success(
                    rankingList ?: (1..8).map { GroupRanking.dummy() }
                )
            }
        }
    }

    @AssistedFactory
    interface GroupRankingViewModelFactory {
        fun create(groupId: Int): GroupRankingViewModel
    }

    companion object {
        private const val TAG = "GroupRankingViewModel"

        @Suppress("UNCHECKED_CAST")
        fun provideGroupRankingViewModelFactory(
            factory: GroupRankingViewModelFactory,
            groupId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                factory.create(groupId = groupId) as T
        }
    }
}