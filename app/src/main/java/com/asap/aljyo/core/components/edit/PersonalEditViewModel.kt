package com.asap.aljyo.core.components.edit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.data.remote.firebase.FCMTokenManager
import com.asap.domain.entity.remote.GroupJoinRequest
import com.asap.domain.entity.remote.UserGroupType
import com.asap.domain.usecase.group.EditPersonalUseCase
import com.asap.domain.usecase.group.JoinGroupUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalEditViewModel @Inject constructor(
    val saveStateHandle: SavedStateHandle,
    private val editPersonalUseCase: EditPersonalUseCase,
    private val joinGroupUseCase: JoinGroupUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PersonalEditState())
    val state = _state.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _complete = MutableSharedFlow<Unit>()
    val complete = _complete.asSharedFlow()

    private var groupId: Int =
        saveStateHandle.get<Int>("groupId") ?: throw IllegalArgumentException("groupId is required")

    init {
        saveStateHandle.get<PersonalEditState>("setting")?.let {
            _state.value = it.copy(isEditMode = true)
        }
        viewModelScope.launch {
            if (!_state.value.isEditMode) {
                _state.value = _state.value.copy(
                    nickName = getUserInfoUseCase()?.nickname ?: ""
                )
            }

            saveStateHandle.getStateFlow("selectedMusic", _state.value.musicTitle).collect {
                _state.value = _state.value.copy(musicTitle = it)
            }
        }
    }

    fun onAlarmTypeSelected(alarmType: String) {
        _state.value = _state.value.copy(
            alarmType = alarmType
        )
    }

    fun onAlarmVolumeSelected(volume: Float) {
        _state.value = _state.value.copy(
            alarmVolume = volume
        )
    }

    fun onCompleteClick() {
        viewModelScope.launch {
            editPersonalUseCase(
                groupId = groupId,
                alarmType = _state.value.alarmType,
                alarmVolume = if (_state.value.alarmType == "VIBRATION") null else _state.value.alarmVolume?.toInt(),
                musicTitle = if (_state.value.alarmType == "VIBRATION") null else _state.value.musicTitle,
            )
        }.invokeOnCompletion {
            if (it == null) {
                viewModelScope.launch {
                    _complete.emit(Unit)
                }
            }
        }
    }

    fun joinGroup() {
        viewModelScope.launch {
            _isLoading.value = true
            val userInfo = getUserInfoUseCase()

            joinGroupUseCase(
                GroupJoinRequest(
                    userId = userInfo?.userId?.toInt() ?: -1,
                    groupId = groupId,
                    alarmType = _state.value.alarmType,
                    deviceToken = FCMTokenManager.token,
                    groupPassword = null,
                )
            ).firstOrNull()
        }.invokeOnCompletion {
            if (it == null) {
                viewModelScope.launch {
                    _isLoading.value = false
                    _complete.emit(Unit)
                }
            }
        }
    }
}