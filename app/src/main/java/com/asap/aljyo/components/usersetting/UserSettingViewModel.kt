package com.asap.aljyo.components.usersetting

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.domain.usecase.user.CheckNicknameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSettingViewModel @Inject constructor(
    private val checkNicknameUseCase: CheckNicknameUseCase
) : ViewModel() {
    private val _userSettingState = MutableStateFlow(UserSettingState())
    val userSettingState: StateFlow<UserSettingState> = _userSettingState

    fun setProfileImage(uri: Uri?) {
        if (uri != null) {
            _userSettingState.value = _userSettingState.value.copy(selectedProfileImage = uri)
        }
    }

    fun updateNickname(nickname: String) {
        val msg = if (nickname.length !in 2..8) UserSettingMsgType.LengthError else UserSettingMsgType.None
        _userSettingState.value = _userSettingState.value.copy(
            nickname = nickname,
            msg = msg
        )
    }

    fun checkNickname(nickname: String) {
        viewModelScope.launch {
            val msg = if (checkNicknameUseCase(nickname)) UserSettingMsgType.Success else UserSettingMsgType.DuplicateNicknameError
            _userSettingState.value = _userSettingState.value.copy(
                msg = msg
            )
        }
    }

}