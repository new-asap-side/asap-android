package com.asap.aljyo.core.components.alarm_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.domain.usecase.group.FetchRankingNumberUseCase
import com.asap.domain.usecase.group.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RankingState(
    val nickname: String = "",
    val profile: String = "",
    val rank: String,
    val score: Int = (if (rank == "-") 0 else 2000 - (rank.toInt() - 1) * 200)
) {
    companion object {
        fun initial() = RankingState(rank = "-")
    }
}

@HiltViewModel
class AlarmResultViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val fetchRankingNumberUseCase: FetchRankingNumberUseCase
) : ViewModel() {
    private val _rankingState = MutableStateFlow(RankingState.initial())
    val rankState get() = _rankingState.asStateFlow()

    fun fetchRankingNumber(groupId: Int) {
        val userDeferred = viewModelScope.async { getUserInfoUseCase() }

        viewModelScope.launch {
            fetchRankingNumberUseCase(groupId = groupId).catch {
                _rankingState.emit(RankingState.initial())
            }.collect { response ->
                response?.let {
                    userDeferred.await()?.let { userInfo ->
                        _rankingState.emit(
                            RankingState(
                                nickname = userInfo.nickname ?: "",
                                profile = userInfo.profileImg ?: "",
                                rank = it.rankNumber.toString()
                            )
                        )
                    }
                }
            }
        }
    }
}