package com.asap.aljyo.components.group_form

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class GroupFormViewModel @Inject constructor(

): ViewModel() {
    private val _groupScreenState = MutableStateFlow(GroupScreenState())
    val groupScreenState: StateFlow<GroupScreenState> get() = _groupScreenState.asStateFlow()

    private val _alarmScreenState = MutableStateFlow(AlarmScreenState())
    val alarmScreenState: StateFlow<AlarmScreenState> get() = _alarmScreenState.asStateFlow()

    fun onGroupTypeSelected(groupType: Boolean, password: String) {
        _groupScreenState.value = _groupScreenState.value.copy(
            isPublic = groupType,
            groupPassword = password
        )
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

    fun onAlarmMusicSelected(music: String) {
        _alarmScreenState.value = _alarmScreenState.value.copy(
            musicTitle = music
        )
    }

    fun onAlarmVolumeSelected(volume: Float) {
        _alarmScreenState.value = _alarmScreenState.value.copy(
            alarmVolume = volume
        )
    }

    fun onCompleteClicked() {
        // 서버 요청 보내기
        // AlarmType이 진동이라면 musicTitle & Volume = null
    }
}