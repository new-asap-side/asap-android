package com.asap.aljyo.core.components.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.domain.entity.local.User
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
open class UserViewModel @Inject constructor(
    userInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user get() = _user.asStateFlow()

    init {
        viewModelScope.launch {
            _user.emit(userInfoUseCase().also { Log.v(TAG, "$it") })
        }
    }

    companion object {
        const val TAG = "UserViewModel"
    }
}