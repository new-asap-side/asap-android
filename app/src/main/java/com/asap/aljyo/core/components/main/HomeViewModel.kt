package com.asap.aljyo.core.components.main

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.main.home.PrivateGroupState
import com.asap.data.remote.firebase.FCMTokenManager
import com.asap.domain.entity.local.User
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.entity.remote.GroupJoinRequest
import com.asap.domain.entity.remote.GroupJoinResponse
import com.asap.domain.usecase.group.FetchGroupDetailsUseCase
import com.asap.domain.usecase.group.FetchLatestGroupUseCase
import com.asap.domain.usecase.group.JoinGroupUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchLatestGroupUseCase: FetchLatestGroupUseCase,
    private val fetchGroupDetailsUseCase: FetchGroupDetailsUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val joinGroupUseCase: JoinGroupUseCase
) : ViewModel() {
    private val _latestGroupState = MutableStateFlow<UiState<List<AlarmGroup>?>>(UiState.Loading)
    val latestGroupState get() = _latestGroupState.asStateFlow()

    val selectedGroupId = mutableStateOf<Int?>(null)

    private val _joinResponseState =
        MutableStateFlow<RequestState<GroupJoinResponse?>>(RequestState.Initial)
    val joinResponseState get() = _joinResponseState.asStateFlow()

    val nickname get() = userInfo.value?.nickname ?: ""

    private val userInfo = mutableStateOf<User?>(null)

    private val _scrollPositionMap = mutableMapOf(
        MAIN_TAB_SCROLL_KEY to Pair(0, 0),
        POPULAR_TAB_SCROLL_KEY to Pair(0, 0),
        LATEST_TAB_SCROLL_KEY to Pair(0, 0),
    )
    val scrollPositionMap get() = _scrollPositionMap

    private val _error = mutableStateOf(false)
    val error get() = _error.value

    private val _privateGroupState = mutableStateOf(PrivateGroupState())
    val privateGroupState get() = _privateGroupState.value

    init {
        viewModelScope.launch {
            userInfo.value = getUserInfoUseCase()
        }
    }

    fun fetchHomeData(internal: Boolean = false) = viewModelScope.launch {
        _error.value = false
        if (!internal) {
            _latestGroupState.value = UiState.Loading
        }

        fetchLatestGroupUseCase()
            .catch { e -> _latestGroupState.value = handleThrowable(e) }
            .collect { latestGroup -> _latestGroupState.value = UiState.Success(latestGroup) }
    }

    fun saveScrollPosition(key: String, index: Int, offset: Int) {
        _scrollPositionMap[key] = Pair(index, offset)
    }

    fun checkJoinedGroup(groupId: Int) = viewModelScope.launch {
        fetchGroupDetailsUseCase(groupId = groupId).catch { e ->
            Log.e(TAG, "$e")
            handleThrowable(e)
        }.collect { details ->
            val joined = details?.users?.find {
                it.userId.toString() == userInfo.value?.userId
            } != null

            _privateGroupState.value = _privateGroupState.value.copy(
                showPasswordSheet = !joined,
                isJoinedGroup = joined,
            )
        }
    }

    fun joinGroup(password: String, alarmType: String) = viewModelScope.launch {
        val userInfo = getUserInfoUseCase()
        _joinResponseState.value = RequestState.Loading
        joinGroupUseCase(
            GroupJoinRequest(
                userId = (userInfo?.userId?.toInt() ?: -1),
                groupId = (selectedGroupId.value ?: -1),
                deviceToken = FCMTokenManager.token,
                groupPassword = password,
                alarmType = alarmType,
            )
        ).catch { e ->
            Log.e(TAG, "$e")
            val errorCode = when (e) {
                is HttpException -> e.code()
                else -> -1
            }

            _joinResponseState.value = RequestState.Error(errorCode)
        }.collect { result ->
            _joinResponseState.value = RequestState.Success(result)
        }
    }

    fun joinStateClear() {
        _joinResponseState.value = RequestState.Initial
    }

    fun clearPrivateGroupState() {
        _privateGroupState.value = PrivateGroupState()
    }

    private fun handleThrowable(e: Throwable): UiState.Error {
        _error.value = true
        return when (e) {
            is HttpException -> UiState.Error(e.code())
            else -> UiState.Error(-1)
        }
    }

    companion object {
        const val TAG = "HomeViewModel"

        const val MAIN_TAB_SCROLL_KEY = "main"
        const val POPULAR_TAB_SCROLL_KEY = "popular"
        const val LATEST_TAB_SCROLL_KEY = "latest"
    }
}