package com.asap.aljyo.core.components.report

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.domain.usecase.user.DeleteUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
) : ViewModel() {
    private val _survey = MutableStateFlow("")
    val survey: StateFlow<String> get() = _survey

    fun updateSurvey(input: String) {
        _survey.value = input
    }
}