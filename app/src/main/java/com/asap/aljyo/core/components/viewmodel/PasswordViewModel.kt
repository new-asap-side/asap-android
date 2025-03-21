package com.asap.aljyo.core.components.viewmodel

import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.data.remote.TokenManager
import com.asap.domain.entity.remote.GroupJoinRequest
import com.asap.domain.entity.remote.GroupJoinResponse
import com.asap.domain.usecase.group.GetUserInfoUseCase
import com.asap.domain.usecase.group.JoinGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val joinGroupUseCase: JoinGroupUseCase,
) : NetworkViewModel() {
    override val prefix: String = "Password"

    private val _showSheet = MutableSharedFlow<Boolean>()
    val showSheet get() = _showSheet.asSharedFlow()

    private val _joinState = MutableStateFlow<RequestState<GroupJoinResponse>>(RequestState.Initial)
    val joinState get() = _joinState.asStateFlow()

    fun showSheet() {
        viewModelScope.launch {
            _showSheet.emit(true)
        }
    }

    fun hideSheet() {
        viewModelScope.launch {
            _showSheet.emit(false)
            _joinState.emit(RequestState.Initial)
        }
    }

    fun join(groupId: Int, password: String) {
        viewModelScope.launch {
            val request = getUserInfoUseCase()?.run {
                GroupJoinRequest(
                    userId = userId.toInt(),
                    groupId = groupId,
                    deviceToken = TokenManager.fcmToken,
                    groupPassword = password
                )
            } ?: return@launch

            _joinState.emit(RequestState.Loading)
            joinGroupUseCase(request).catch { e ->
                _joinState.emit(handleRequestThrowable(e))
            }.collect { result ->
                result?.let {
                    _joinState.emit(RequestState.Success(result))
                }
            }
        }
    }

}