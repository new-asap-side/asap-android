package com.asap.aljyo.core.components.edit

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.core.components.group_form.GroupScreenState
import com.asap.domain.usecase.group.EditGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val editGroupUseCase: EditGroupUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(GroupEditState())
    val state: StateFlow<GroupEditState> get() = _state.asStateFlow()

    private val _complete = MutableSharedFlow<Unit>()
    val complete = _complete.asSharedFlow()

    init {
        savedStateHandle.get<GroupEditState>("groupDetail")?.let {
            _state.value = it
        }
    }

    fun onAlarmTypeSelected(content: String) {
        _state.value = _state.value.copy(alarmUnlockContents = content)
    }

    fun onGroupTypeSelected(type: Boolean) {
        _state.value = _state.value.copy(isPublic = type)
    }

    fun onGroupPasswordChanged(pw: String) {
        if (pw.length > 4) return

        _state.value = _state.value.copy(groupPassword = pw)
    }

    fun onGroupImageSelected(image: Uri?) {
        if (image != null) {
            _state.value = _state.value.copy(groupImage = image.toString())
        }
    }

    fun onGroupTitleChanged(title: String) {
        if (title.length > 30) return

        _state.value = _state.value.copy(title = title)
    }

    fun onGroupDescriptionChanged(description: String) {
        if (description.length > 50) return

        _state.value = _state.value.copy(description = description)
    }

    fun onGroupPersonSelected(person: Int) {
        _state.value = _state.value.copy(currentPerson = person)
    }

    fun onCompleteClick() {
        viewModelScope.launch {
            editGroupUseCase(
                groupId = _state.value.groupId,
                title = _state.value.title,
                description = _state.value.description,
                maxPerson = _state.value.currentPerson,
                alarmUnlockContents = _state.value.alarmUnlockContents,
                isPublic = _state.value.isPublic
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