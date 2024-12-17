package com.asap.aljyo.core.components.withdrawal

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.domain.usecase.user.DeleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val deleteUseCase: DeleteUseCase
) : ViewModel() {
    private val _selectedIndex = mutableStateOf<Int?>(null)
    val selectedIndex get() = _selectedIndex.value

    private val _withdrawalState = MutableStateFlow<RequestState<Boolean>>(RequestState.Initial)
    val withdrawalState get() = _withdrawalState.asStateFlow()

    private val _checkedPrecautions = mutableStateOf(false)
    val checkedPrecautions get() = _checkedPrecautions.value

    var survey: String = ""

    fun select(index: Int?) {
        _selectedIndex.value = index
    }

    fun checkPrecautions() {
        _checkedPrecautions.value = !_checkedPrecautions.value
    }

    fun deleteUser() = viewModelScope.launch {
        _withdrawalState.value = RequestState.Loading
        deleteUseCase.invoke(survey).catch { e ->
            when (e) {
                is HttpException -> {
                    // TODO error handling
                    _withdrawalState.value = RequestState.Success(true)
                }
            }
        }.collect {
            _withdrawalState.value = RequestState.Success(true)
        }
    }
}