package com.asap.aljyo.core.components.group_form

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.util.PictureUtil
import com.asap.aljyo.util.format
import com.asap.data.remote.firebase.FCMTokenManager
import com.asap.domain.usecase.group.CreateGroupUseCase
import com.asap.domain.usecase.group.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class GroupFormViewModel @Inject constructor(
    val saveStateHandle: SavedStateHandle,
    private val createGroupUseCase: CreateGroupUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {
    private val _groupScreenState = MutableStateFlow(GroupScreenState())
    val groupScreenState: StateFlow<GroupScreenState> get() = _groupScreenState.asStateFlow()

    private val _alarmScreenState = MutableStateFlow(AlarmScreenState())
    val alarmScreenState: StateFlow<AlarmScreenState> get() = _alarmScreenState.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private val _complete = MutableSharedFlow<Int?>()
    val complete = _complete.asSharedFlow()

    private var groupId: Int? = null

    init {
        viewModelScope.launch {
            _alarmScreenState.value = _alarmScreenState.value.copy(
                nickName = getUserInfoUseCase()?.nickname
            )

            saveStateHandle.getStateFlow("selectedMusic", _alarmScreenState.value.musicTitle)
                .collect {
                    _alarmScreenState.value = _alarmScreenState.value.copy(musicTitle = it)
                }
        }
    }

    fun onGroupTypeSelected(groupType: Boolean) {
        _groupScreenState.value = _groupScreenState.value.copy(
            isPublic = groupType,
            groupPassword = if (groupType) null else ""
        )
    }

    fun onGroupPasswordChanged(pw: String) {
        if (pw.length > 4) return

        _groupScreenState.value = _groupScreenState.value.copy(groupPassword = pw)
    }

    fun onGroupImageSelected(image: Uri?) {
        if (image != null) {
            _groupScreenState.value = _groupScreenState.value.copy(groupImage = image)
        }
    }

    fun onGroupTitleChanged(title: String) {
        if (title.length > 30) return

        _groupScreenState.value = _groupScreenState.value.copy(title = title)
    }

    fun onGroupDescriptionChanged(description: String) {
        if (description.length > 50) return

        _groupScreenState.value = _groupScreenState.value.copy(description = description)
    }

    fun onGroupPersonSelected(person: Int) {
        _groupScreenState.value = _groupScreenState.value.copy(maxPerson = person)
    }

    fun onAlarmTimeSelected(hour: String, minutes: String) {
        val selectedTime = "$hour:$minutes"

        _groupScreenState.value = _groupScreenState.value.copy(alarmTime = selectedTime)
    }

    fun onAlarmDaysSelected(day: String) {
        val currentDays = _groupScreenState.value.alarmDays.toMutableList()
            .apply {
                if (contains(day)) remove(day) else add(day)
            }

        _groupScreenState.value = _groupScreenState.value.copy(alarmDays = currentDays)
    }

    fun onAlarmEndDateSelected(date: LocalDate) {
        _groupScreenState.value = _groupScreenState.value.copy(alarmEndDate = date)
    }

    fun onAlarmUnlockContentsSelected(contents: String) {
        _alarmScreenState.value = _alarmScreenState.value.copy(
            alarmUnlockContents = contents
        )
    }

    fun onAlarmTypeSelected(alarmType: String) {
        _alarmScreenState.value = _alarmScreenState.value.copy(
            alarmType = alarmType,
        )
    }

    fun onAlarmVolumeSelected(volume: Float) {
        _alarmScreenState.value = _alarmScreenState.value.copy(
            alarmVolume = volume
        )
    }

    fun onCompleteClicked() {
        viewModelScope.launch {
            _isLoading.value = true
            val volume = if (_alarmScreenState.value.alarmType == "VIBRATION") {
                null
            } else {
                _alarmScreenState.value.alarmVolume
            }

            createGroupUseCase(
                groupImage = PictureUtil.encodeType(_groupScreenState.value.groupImage)
                    ?: throw IllegalArgumentException("image encoded fail"),
                alarmDay = _groupScreenState.value.alarmDays,
                alarmEndDate = _groupScreenState.value.alarmEndDate!!.format(),
                alarmTime = _groupScreenState.value.alarmTime,
                alarmType = _alarmScreenState.value.alarmType,
                alarmUnlockContents = _alarmScreenState.value.alarmUnlockContents,
                alarmVolume = volume,
                description = _groupScreenState.value.description,
                deviceType = "ANDROID",
                groupPassword = _groupScreenState.value.groupPassword,
                isPublic = _groupScreenState.value.isPublic!!,
                maxPerson = _groupScreenState.value.maxPerson,
                title = _groupScreenState.value.title,
                musicTitle = if (_alarmScreenState.value.alarmType == "VIBRATION") null else _alarmScreenState.value.musicTitle,
                deviceToken = FCMTokenManager.token
            ).let { id ->
                groupId = id
            }
        }.invokeOnCompletion {
            viewModelScope.launch {
                _isLoading.value = false
                _complete.emit(groupId)
            }
        }
    }
}