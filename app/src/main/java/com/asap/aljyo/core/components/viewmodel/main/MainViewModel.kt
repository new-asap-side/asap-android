package com.asap.aljyo.core.components.viewmodel.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import asap.aljyo.core.event.Network
import asap.aljyo.core.event.Patch
import com.asap.domain.usecase.alarm.PatchAlarmTokenUseCase
import com.asap.domain.usecase.user.DeleteLocalUserInfoUseCase
import com.asap.domain.usecase.user.FetchProfileItemUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val deleteLocalUserInfoUseCase: DeleteLocalUserInfoUseCase,
    private val fetchProfileItemUseCase: FetchProfileItemUseCase,
    private val patchAlarmTokenUseCase: PatchAlarmTokenUseCase,
    private val sp: SharedPreferences
) : ViewModel() {
    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex get() = _selectedIndex.asStateFlow()

    private val _isNew = MutableStateFlow(false)
    val isNew = _isNew.asStateFlow()

    private val isFirst = sp.getBoolean("is_first_launch", true)

    private val _networkEvent = MutableSharedFlow<Network<Unit, Boolean>>(replay = 1)
    val networkEvent get() = _networkEvent.asSharedFlow()

    init {
        if (isFirst) {
            viewModelScope.launch {
                val userId = getUserInfoUseCase()?.userId ?: -1
                val totalScore = fetchProfileItemUseCase(userId.toString()).totalRankScore
                val completeMilestones = listOf(
                    20000,
                    50000,
                    100000,
                    2000000,
                    400000,
                    700000
                ).filter { it <= totalScore }
                    .map { it.toString() }.toSet()

                sp.edit().putStringSet("alert_milestones", completeMilestones).apply()
                sp.edit().putBoolean("is_first_launch", false).apply()
            }
        }
    }

    fun getProfileItemNotification() {
        viewModelScope.launch {
            val userId = getUserInfoUseCase()?.userId ?: -1

            _isNew.emit(fetchProfileItemUseCase(userId.toString()).profileItems.any { it.isRedPoint })
        }
    }

    fun select(index: Int) = viewModelScope.launch {
        _selectedIndex.emit(index)
    }

    fun deleteUserInfo() {
        viewModelScope.launch {
            deleteLocalUserInfoUseCase()
        }
    }

    suspend fun patchToken(scope: CoroutineScope) {
        _networkEvent.emit(
            Patch(scope) { patchAlarmTokenUseCase() }
        )
    }
}