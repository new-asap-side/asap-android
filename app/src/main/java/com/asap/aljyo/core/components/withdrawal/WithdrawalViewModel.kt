package com.asap.aljyo.core.components.withdrawal

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.domain.usecase.user.DeleteLocalUserInfoUseCase
import com.asap.domain.usecase.user.DeleteUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val deleteUserInfoUseCase: DeleteUserInfoUseCase,
    private val deleteLocalUserInfoUseCase: DeleteLocalUserInfoUseCase
) : ViewModel() {
    private val _selectedIndex = mutableStateOf<Int?>(null)
    val selectedIndex get() = _selectedIndex.value

    private val _withdrawalState = MutableStateFlow<RequestState<Boolean>>(RequestState.Initial)
    val withdrawalState get() = _withdrawalState.asStateFlow()

    private val _checkedPrecautions = mutableStateOf(false)
    val checkedPrecautions get() = _checkedPrecautions.value

    private val _survey = mutableStateOf("")

    val enable get() = _checkedPrecautions.value && _survey.value.isNotEmpty()

    fun select(index: Int?) {
        _selectedIndex.value = index
    }

    fun inputSurvey(input: String) {
        _survey.value = input
    }

    fun checkPrecautions() {
        _checkedPrecautions.value = !_checkedPrecautions.value
    }

    fun deleteUser() = viewModelScope.launch {
        _withdrawalState.value = RequestState.Loading
        deleteUserInfoUseCase(_survey.value).catch { e ->
            Log.e(TAG, "$e")
            val errorCode = when (e) {
                is HttpException -> e.code()
                else -> -1
            }

            _withdrawalState.value = RequestState.Error(errorCode)
        }.collect { response ->
            Log.e(TAG, "$response")
            val result = response?.result ?: false
            if (result) {
                deleteLocalUserInfoUseCase()
                _withdrawalState.value = RequestState.Success(true)
            }
        }
    }

    companion object {
        const val TAG = "WithdrawalViewModel"
    }
}