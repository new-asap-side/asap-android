package com.asap.aljyo.core.components.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.util.PictureUtil
import com.asap.domain.usecase.group.GetUserInfoUseCase
import com.asap.domain.usecase.user.DeleteLocalUserInfoUseCase
import com.asap.domain.usecase.user.FetchProfileItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val deleteLocalUserInfoUseCase: DeleteLocalUserInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val fetchProfileItemUseCase: FetchProfileItemUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<MyPageState>>(UiState.Loading)
    val state = _state.asStateFlow()

    private val _fetchScreenFlag = MutableStateFlow(true)

    init {
        fetchScreen()
    }

    fun fetchScreen() {
        if (_fetchScreenFlag.value) {
            viewModelScope.launch {
                val userInfo = getUserInfoUseCase()
                val userId = (userInfo?.userId ?: -1).toString()
                val profileItem = fetchProfileItemUseCase(userId)

                _state.value = UiState.Success(
                    MyPageState(
                        nickName = userInfo?.nickname,
                        profileImage = userInfo?.profileImg,
                        profileItem = PictureUtil.getProfileItemByName(userInfo?.profileItem),
                        profileItemNotification = profileItem.profileItems.count { it.isRedPoint }
                    )
                )
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

