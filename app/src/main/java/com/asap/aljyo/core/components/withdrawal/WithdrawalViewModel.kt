package com.asap.aljyo.core.components.withdrawal

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor() : ViewModel() {
    private val _selectedIndex = mutableStateOf<Int?>(null)
    val selectedIndex get() = _selectedIndex.value

    private val _checkedPrecautions = mutableStateOf(false)
    val checkedPrecautions get() = _checkedPrecautions.value

    fun select(index: Int?) {
        _selectedIndex.value = index
    }

    fun checkPrecautions() {
        _checkedPrecautions.value = !_checkedPrecautions.value
    }
}