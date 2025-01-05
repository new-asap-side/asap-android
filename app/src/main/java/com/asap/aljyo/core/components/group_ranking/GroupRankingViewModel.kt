package com.asap.aljyo.core.components.group_ranking

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.domain.entity.remote.GroupRanking
import com.asap.domain.usecase.group.FetchGroupRankingUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException

class GroupRankingViewModel @AssistedInject constructor(
    fetchGroupRankingUseCase: FetchGroupRankingUseCase,
    getUserInfoUseCase: GetUserInfoUseCase,
    @Assisted private val groupId: Int
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<GroupRanking>?>>(UiState.Loading)
    val state get() = _state

    private val _mIndex = mutableStateOf<Int?>(null)
    val mIndex get() = _mIndex.value

    init {
        viewModelScope.launch {
            delay(500)
            fetchGroupRankingUseCase(groupId).catch { e ->
                Log.e(TAG, "fetchGroupRanking error - $e")
                val errorCode = when (e) {
                    is HttpException -> e.code()
                    else -> -1
                }
                 _state.value = UiState.Error(errorCode = errorCode)
            }.collect { rankingList ->
                val mNickname = getUserInfoUseCase().let { it?.nickname }
                _mIndex.value = rankingList?.indexOf(
                    rankingList.find { mNickname == it.nickName }
                )

                _state.value = UiState.Success(rankingList)
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