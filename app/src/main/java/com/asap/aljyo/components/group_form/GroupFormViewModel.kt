package com.asap.aljyo.components.group_form

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.asap.domain.usecase.group.CreateGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class GroupFormViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val createGroupUseCase: CreateGroupUseCase
): ViewModel() {
    private val _groupScreenState = MutableStateFlow(GroupScreenState())
    val groupScreenState: StateFlow<GroupScreenState> get() = _groupScreenState.asStateFlow()

    private val _alarmScreenState = MutableStateFlow(AlarmScreenState())
    val alarmScreenState: StateFlow<AlarmScreenState> get() = _alarmScreenState.asStateFlow()
}