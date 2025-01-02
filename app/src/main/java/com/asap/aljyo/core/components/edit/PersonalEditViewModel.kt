package com.asap.aljyo.core.components.edit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class PersonalEditViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(PersonalEditState())
    val state = _state.asStateFlow()

    private var groupId: Int? = null

    init {
        saveStateHandle.get<Int>("groupId")?.let { groupId = it }
        saveStateHandle.get<PersonalEditState>("setting")?.let { _state.value = it }
    }

    fun onAlarmTypeSelected(alarmType: String) {
        _state.value = _state.value.copy(
            alarmType = alarmType,
            musicTitle = if (alarmType == "VIBRATION") null else _state.value.musicTitle,
            alarmVolume = if (alarmType == "VIBRATION") null else _state.value.alarmVolume
        )
    }

    fun onAlarmMusicSelected(music: String) {
        _state.value = _state.value.copy(
            musicTitle = music
        )
    }

    fun onAlarmVolumeSelected(volume: Float) {
        _state.value = _state.value.copy(
            alarmVolume = volume
        )
    }



}