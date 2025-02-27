package com.asap.aljyo.core.components.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.domain.entity.local.User
import com.asap.domain.usecase.user.DeleteUserInfoUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Channel buffer 내 User 정보 공유
val userChannel = Channel<User?>(Channel.CONFLATED)

@HiltViewModel
open class UserViewModel @Inject constructor(
    userInfoUseCase: GetUserInfoUseCase,
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user get() = _user.asStateFlow()

    init {
        viewModelScope.launch {
            with(userChannel) {
                receive() ?: send(userInfoUseCase()).let {
                    Log.d("UserViewModel", "$it")
                }
            }
        }
    }
}