package com.asap.aljyo.core.components.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.domain.usecase.group.GetUserInfoUseCase
import com.asap.domain.usecase.user.DeleteLocalUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val deleteLocalUserInfoUseCase: DeleteLocalUserInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<MyPageState>>(UiState.Loading)
    val state = _state.asStateFlow()

    private val _fetchScreenFlag = MutableStateFlow(true)

    init {
        fetchScreen()
    }

    fun fetchScreen() {
        Log.d("MypageViewModel:","fetchScreen 함수 실행")
        if (_fetchScreenFlag.value) {
            Log.d("MypageViewModel:","fetchScreen 실행")
            viewModelScope.launch {
                getUserInfoUseCase().let {
                    _state.value = UiState.Success(
                        MyPageState(
                            nickName = it?.nickname,
                            profileImage = it?.profileImg
                        )
                    )
                }
                Log.d("MyPageViewModel:","State: ${_state.value}")
            }
        }
        _fetchScreenFlag.value = false
    }

    fun fetchScreenFlag() {
        _fetchScreenFlag.value = true
    }

    fun deleteLocalUserInfo() = viewModelScope.launch {
        deleteLocalUserInfoUseCase.invoke()
    }
}

