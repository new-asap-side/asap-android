package com.asap.aljyo.core.components.alarm_result

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.domain.usecase.group.FetchRankingNumberUseCase
import com.asap.domain.usecase.group.GetUserInfoUseCase
import com.asap.utility.datetime.DateTimeParser
import com.asap.utility.datetime.TimeColon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RankingState(
    val nickname: String = "",
    val profile: String = "",
    val profileItem: String = "",
    val rank: String,
    val score: Int = (if (rank == "-") 0 else 2000 - (rank.toInt() - 1) * 200)
) {
    companion object {
        fun initial() = RankingState(rank = "-")
    }
}

@HiltViewModel
class AlarmResultViewModel @Inject constructor(
    @TimeColon
    private val parser: DateTimeParser,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val fetchRankingNumberUseCase: FetchRankingNumberUseCase
) : ViewModel() {
    private val timeFlow = flow {
        emit(parser.parse())
    }

    private val _timeCollector = MutableStateFlow("")
    val timeCollector get() = _timeCollector.asStateFlow()

    private val _rankingState = MutableStateFlow(RankingState.initial())
    val rankState get() = _rankingState.asStateFlow()

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                timeFlow.collect {
                    _timeCollector.emit(it)
                }
                delay(1000)
            }
        }
    }

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
                                profileItem = userInfo.profileItem ?: "",
                                rank = it.rankNumber.toString()
                            )
                        )
                    }
                }
            }
        }
    }
}