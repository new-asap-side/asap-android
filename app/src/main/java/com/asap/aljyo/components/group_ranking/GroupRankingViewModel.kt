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
        Log.d("GroupRankingViewModel", "viewModel init block groupId: $groupId")
        viewModelScope.launch {
            fetchGroupRankingUseCase.invoke(groupId).catch {
                // TODO 에러 처리
                // _state.value = UiState.Error("")
                _state.value = UiState.Success((1..8).map { GroupRanking.dummy() })
            }.collect { rankingList ->
                _state.value = UiState.Success(rankingList ?: listOf())
            }
        }
    }

    @AssistedFactory
    interface GroupRankingViewModelFactory {
        fun create(groupId: Int): GroupRankingViewModel
    }

    companion object {
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