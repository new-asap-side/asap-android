package com.asap.aljyo.core.components.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.remote.GroupRanking
import com.asap.domain.usecase.group.GetUserInfoUseCase
import com.asap.domain.usecase.group.GroupRankingUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Immutable
data class RankingScreenState(
    val selectedTabIndex: Int,
    val totalRanks: List<GroupRanking>? = emptyList(),
    val todayRanks: List<GroupRanking>? = emptyList()
)

class GroupRankingViewModel @AssistedInject constructor(
    @Assisted private val groupId: Int,
    private val rankingUseCase: GroupRankingUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : NetworkViewModel() {
    override val prefix: String = "GroupRanking"

    private val selectedTabIndex = MutableStateFlow(0)
    private val totalRanking = MutableStateFlow<List<GroupRanking>?>(emptyList())
    private val todayRanking = MutableStateFlow<List<GroupRanking>?>(emptyList())

    private val _mIndex = mutableStateOf<Int?>(null)
    val mIndex get() = _mIndex.value ?: -1

    val rankingState: StateFlow<UiState<RankingScreenState>> = combine(
        selectedTabIndex,
        totalRanking,
        todayRanking
    ) { index, total, today ->
        fetchGroupRanking()
        fetchTodayRanking()

        if ((total == null && index == 0) || (today == null && index == 1)) {
            UiState.Error()
        } else {
            UiState.Success(RankingScreenState(index, total, today))
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        UiState.Loading
    )

    fun fetchGroupRanking() {
        viewModelScope.launch(Dispatchers.Default) {
            rankingUseCase.fetchGroupRankingUseCase(groupId).catch {
                todayRanking.emit(null)
            }.collect { result ->
                result?.let { totalRanks ->
                    totalRanks.run {
                        _mIndex.value = indexOfFirst { ranks ->
                            ranks.nickName == getUserInfoUseCase()?.nickname
                        }
                        totalRanking.emit(totalRanks)
                    }
                }
            }
        }
    }

    fun fetchTodayRanking() {
        viewModelScope.launch(Dispatchers.Default) {
            rankingUseCase.fetchTodayRankingUseCase(groupId).catch {
                todayRanking.emit(null)
            }.collect { ressult ->
                ressult?.let { todayRanks ->
                    todayRanking.emit(todayRanks)
                }
            }
        }
    }

    fun selectTab(index: Int) {
        viewModelScope.launch {
            selectedTabIndex.emit(index)
        }
    }

    // 1, 2, 3등
    fun sliceRanks(input: List<GroupRanking>): List<GroupRanking> {
        return if (input.size < 3) input else input.subList(0, 3)
    }

    // 4등 이하
    fun sliceUnRanks(input: List<GroupRanking>): List<GroupRanking> {
        return if (input.size < 3) emptyList() else input.drop(3)
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