package com.asap.aljyo.components.usersetting

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserSettingViewModel @Inject constructor() : ViewModel() {
    private val _userSettingState = MutableStateFlow(UserSettingState())
    val userSettingState: StateFlow<UserSettingState> = _userSettingState

    fun setProfileImage(uri: Uri?) {
        if (uri != null) {
            _userSettingState.value = _userSettingState.value.copy(selectedProfileImage = uri)
        }
    }

    fun updateNickname(nickname: String) {
        val error = if (nickname.length !in 2..8) UserSettingErrorType.LengthError else UserSettingErrorType.None
        _userSettingState.value = _userSettingState.value.copy(
            nickname = nickname,
            errorMsg = error
        )
    }

}