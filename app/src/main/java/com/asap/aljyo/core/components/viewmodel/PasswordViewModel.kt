package com.asap.aljyo.core.components.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.core.components.main.HomeViewModel.Companion.TAG
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.UiState
import com.asap.data.remote.firebase.FCMTokenManager
import com.asap.domain.entity.remote.GroupJoinRequest
import com.asap.domain.entity.remote.GroupJoinResponse
import com.asap.domain.usecase.group.FetchGroupDetailsUseCase
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
    private val fetchGroupDetailsUseCase: FetchGroupDetailsUseCase,
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
        }
    }

    fun checkJoined(groupId: Int) {
        viewModelScope.launch {
            fetchGroupDetailsUseCase(groupId = groupId).catch { e ->
                Log.e(TAG, "$e")
                handleThrowable(e)
            }.collect { details ->
                getUserInfoUseCase()?.let { user ->
                    details?.users?.find { it.userId.toString() == user.userId }
                } != null
            }
        }
    }

    fun join(groupId: Int, password: String) {
        viewModelScope.launch {
            val request = getUserInfoUseCase()?.run {
                GroupJoinRequest(
                    userId = userId.toInt(),
                    groupId = groupId,
                    deviceToken = FCMTokenManager.token,
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