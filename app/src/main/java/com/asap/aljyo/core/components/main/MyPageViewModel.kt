package com.asap.aljyo.core.components.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.domain.entity.local.User
import com.asap.domain.usecase.user.DeleteLocalUserInfoUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val deleteLocalUserInfoUseCase: DeleteLocalUserInfoUseCase
) : ViewModel() {
    private val userInfo = mutableStateOf<User?>(null)

    val nickname get() = userInfo.value?.nickname ?: ""
    val profile get() = userInfo.value?.profileImg ?: ""

    init {
        viewModelScope.launch {
            userInfo.value = getUserInfoUseCase()
        }
    }

    fun deleteLocalUserInfo() = viewModelScope.launch {
        deleteLocalUserInfoUseCase.invoke()
    }

}