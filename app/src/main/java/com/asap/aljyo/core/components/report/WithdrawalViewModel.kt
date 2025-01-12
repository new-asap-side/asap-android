package com.asap.aljyo.core.components.report

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.domain.usecase.group.ReportGroupUseCase
import com.asap.domain.usecase.user.DeleteUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val reportGroupUseCase: ReportGroupUseCase
) : ViewModel() {
    private val _survey = MutableStateFlow("")
    val survey: StateFlow<String> get() = _survey

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private val _complete = MutableSharedFlow<Unit>()
    val complete = _complete.asSharedFlow()

    private var groupId: Int = savedStateHandle.get<Int>("groupId") ?: throw IllegalArgumentException("groupId is required")

    fun updateSurvey(input: String) {
        _survey.value = input
    }

    fun reportGroup(survey: String) {
        _isLoading.value = true
        viewModelScope.launch {
            reportGroupUseCase(groupId, survey)
        }.invokeOnCompletion {
            viewModelScope.launch {
                if (it == null) {
                    _complete.emit(Unit)
                    _isLoading.value = false
                }
            }
        }
    }
}