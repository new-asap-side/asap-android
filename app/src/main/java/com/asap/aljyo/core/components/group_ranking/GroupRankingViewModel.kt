package com.asap.aljyo.core.components.group_ranking

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.core.components.viewmodel.NetworkViewModel
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.remote.GroupRanking
import com.asap.domain.usecase.group.FetchGroupRankingUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@Stable
data class RankingScreenState(
    val selectedTabIndex: Int,
    val ranks: List<GroupRanking> = emptyList()
)

class GroupRankingViewModel @AssistedInject constructor(
    @Assisted private val groupId: Int,
    private val fetchGroupRankingUseCase: FetchGroupRankingUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : NetworkViewModel() {
    override val prefix: String = "GroupRanking"

    private val _state = MutableStateFlow<UiState<RankingScreenState>>(UiState.Loading)
    val state get() = _state.asStateFlow()

    private val _mIndex = mutableStateOf<Int?>(null)
    val mIndex get() = _mIndex.value

    init {
        fetchGroupRanking()
    }

    fun fetchGroupRanking() = viewModelScope.launch {
        _state.value = UiState.Loading
        fetchGroupRankingUseCase(groupId).catch { e ->
            _state.emit(handleThrowable(e))
        }.collect { result ->
            result?.let { ranks ->
                _mIndex.value = getUserInfoUseCase()?.let { user ->
                    ranks.indexOf(ranks.find { user.nickname == it.nickName })
                }

                _state.emit(
                    UiState.Success(RankingScreenState(0, ranks))
                )
            }
        }
    }

    fun getRankList(): List<GroupRanking> {
        if (_state.value !is UiState.Success) {
            return emptyList()
        }

        return (_state.value as UiState.Success).data.ranks.let { ranks ->
            if (ranks.size < 3) ranks else ranks.subList(0, 3)
        }
    }

    fun getUnRankList(): List<GroupRanking> {
        if (_state.value !is UiState.Success) {
            return emptyList()
        }

        return (_state.value as UiState.Success).data.ranks.let { ranks ->
            if (ranks.size < 3) emptyList() else ranks.drop(3)
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