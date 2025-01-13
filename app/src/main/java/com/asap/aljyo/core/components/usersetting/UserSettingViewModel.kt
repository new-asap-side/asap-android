package com.asap.aljyo.core.components.usersetting

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.util.PictureUtil
import com.asap.domain.usecase.user.CheckNicknameUseCase
import com.asap.domain.usecase.user.SaveUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSettingViewModel @Inject constructor(
    private val checkNicknameUseCase: CheckNicknameUseCase,
    private val saveUserProfileUseCase: SaveUserProfileUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _isEditMode = MutableStateFlow(false)

    private val _userSettingState = MutableStateFlow(UserSettingState())
    val userSettingState: StateFlow<UserSettingState> get() = _userSettingState

    private val _requestState = mutableStateOf<RequestState<Boolean>>(RequestState.Initial)
    val requestState get() = _requestState.value

    private var _previousProfileImage: Uri? = null

    val isButtonEnabled: StateFlow<Boolean> = combine(
        _isEditMode, _userSettingState
    ) { isEditMode, userSettingState ->
        if (isEditMode) {
            val isProfileChange = userSettingState.selectedProfileImage != _previousProfileImage
            val isNicknameValid = userSettingState.msg == UserSettingMsgType.None || userSettingState.msg == UserSettingMsgType.Success

            if (isProfileChange) isNicknameValid else userSettingState.msg == UserSettingMsgType.Success
        } else {
            userSettingState.selectedProfileImage != null && userSettingState.msg == UserSettingMsgType.Success
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        savedStateHandle.get<String>("nickName")?.let {
            _userSettingState.value = _userSettingState.value.copy(
                nickname = Uri.decode(it)
            )
        }
        savedStateHandle.get<String>("profileImage").let {
            _userSettingState.value = _userSettingState.value.copy(
                selectedProfileImage = it?.toUri()
            )
        }

        _previousProfileImage = userSettingState.value.selectedProfileImage
    }

    fun setEditMode(isEdit: Boolean) {
        _isEditMode.value = isEdit
    }

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
                val result = saveUserProfileUseCase(nickname, PictureUtil.encodeType(it))
                _requestState.value = RequestState.Success(result)
            }
        }
    }
}