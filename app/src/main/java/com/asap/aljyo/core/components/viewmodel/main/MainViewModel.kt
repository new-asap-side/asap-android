package com.asap.aljyo.core.components.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex get() = _selectedIndex.asStateFlow()

    fun select(index: Int) = viewModelScope.launch {
        _selectedIndex.emit(index)
    }
}