package com.asap.aljyo.core.components.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.domain.usecase.user.FetchProfileItemUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val fetchProfileItemUseCase: FetchProfileItemUseCase
) : ViewModel() {
    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex get() = _selectedIndex.asStateFlow()
    
    private val _isNew = MutableStateFlow(false)
    val isNew = _isNew.asStateFlow()

    init {
        Log.d("MainViewModel", "main view model init")
    }

    fun getProfileItemNotification() {
        viewModelScope.launch {
            val userId = getUserInfoUseCase()?.userId ?: -1

            _isNew.emit(fetchProfileItemUseCase(userId.toString()).profileItems.any { it.isRedPoint })
        }
    }

    fun select(index: Int) = viewModelScope.launch {
        _selectedIndex.emit(index)
    }
}