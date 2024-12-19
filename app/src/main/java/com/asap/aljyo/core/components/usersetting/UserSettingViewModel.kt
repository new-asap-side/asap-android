package com.asap.aljyo.core.components.usersetting

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.util.PictureUtil
import com.asap.domain.usecase.user.CheckNicknameUseCase
import com.asap.domain.usecase.user.SaveUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSettingViewModel @Inject constructor(
    private val checkNicknameUseCase: CheckNicknameUseCase,
    private val saveUserProfileUseCase: SaveUserProfileUseCase
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
        val nicknameRegex =  "[ㄱ-ㅎㅏ-ㅣ]".toRegex()

        if (nicknameRegex.containsMatchIn(nickname)) {
            _userSettingState.value = _userSettingState.value.copy(
                msg = UserSettingMsgType.FormatError
            )
            return
        }

        viewModelScope.launch {
            val msg = if (checkNicknameUseCase(nickname)) UserSettingMsgType.Success else UserSettingMsgType.DuplicateNicknameError

            _userSettingState.value = _userSettingState.value.copy(
                msg = msg
            )
        }
    }

    fun saveUserProfile() {
        viewModelScope.launch {
            val nickname = _userSettingState.value.nickname
            val profileImage = _userSettingState.value.selectedProfileImage

            profileImage?.let {
                saveUserProfileUseCase(nickname, PictureUtil.getStringFromUri(it))
            }
        }
    }
}