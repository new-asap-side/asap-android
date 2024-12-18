package com.asap.aljyo.core.components.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.domain.usecase.user.DeleteLocalUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val deleteLocalUserInfoUseCase: DeleteLocalUserInfoUseCase
) : ViewModel() {

    fun deleteLocalUserInfo() = viewModelScope.launch {
        deleteLocalUserInfoUseCase.invoke()
    }

}