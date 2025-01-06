package com.asap.aljyo.core.components.edit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.domain.usecase.group.EditPersonalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalEditViewModel @Inject constructor(
    val saveStateHandle: SavedStateHandle,
    private val editPersonalUseCase: EditPersonalUseCase
): ViewModel() {

    private val _state = MutableStateFlow(PersonalEditState())
    val state = _state.asStateFlow()

    private val _complete = MutableSharedFlow<Unit>()
    val complete = _complete.asSharedFlow()

    private var groupId: Int = saveStateHandle.get<Int>("groupId") ?: throw IllegalArgumentException("groupId is required")

    init {
        saveStateHandle.get<PersonalEditState>("setting")?.let { _state.value = it }
        viewModelScope.launch {
            saveStateHandle.getStateFlow("selectedMusic", _state.value.musicTitle)
                .collect {
                    _state.value = _state.value.copy(musicTitle = it)
                }
        }
    }

    fun onAlarmTypeSelected(alarmType: String) {
        _state.value = _state.value.copy(
            alarmType = alarmType,
            musicTitle = if (alarmType == "VIBRATION") null else _state.value.musicTitle,
            alarmVolume = if (alarmType == "VIBRATION") null else _state.value.alarmVolume
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
                alarmVolume = _state.value.alarmVolume?.toInt(),
                musicTitle = _state.value.musicTitle
            )
        }.invokeOnCompletion {
            if (it == null) {
                viewModelScope.launch {
                    _complete.emit(Unit)
                }
            }
        }
    }
}