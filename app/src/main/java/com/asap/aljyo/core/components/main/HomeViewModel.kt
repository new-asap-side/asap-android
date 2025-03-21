package com.asap.aljyo.core.components.main

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.UiState
import com.asap.aljyo.ui.composable.main.home.PrivateGroupState
import com.asap.data.remote.TokenManager
import com.asap.domain.entity.local.User
import com.asap.domain.entity.remote.GroupJoinRequest
import com.asap.domain.entity.remote.GroupJoinResponse
import com.asap.domain.usecase.group.FetchGroupDetailsUseCase
import com.asap.domain.usecase.group.JoinGroupUseCase
import com.asap.domain.usecase.user.FetchProfileItemUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchGroupDetailsUseCase: FetchGroupDetailsUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val joinGroupUseCase: JoinGroupUseCase,
    private val fetchProfileItemUseCase: FetchProfileItemUseCase,
    private val sp: SharedPreferences
) : ViewModel() {
    val selectedGroupId = mutableStateOf<Int?>(null)

    private val _joinResponseState =
        MutableStateFlow<RequestState<GroupJoinResponse?>>(RequestState.Initial)
    val joinResponseState get() = _joinResponseState.asStateFlow()

    val nickname get() = userInfo.value?.nickname ?: ""

    private val userInfo = mutableStateOf<User?>(null)

    private val _scrollPositionMap = mutableMapOf(
        MAIN to Pair(0, 0),
        POPULAR to Pair(0, 0),
        LATEST to Pair(0, 0),
    )
    val scrollPositionMap: Map<String, Pair<Int, Int>>
        get() = _scrollPositionMap.toMap()

    private val _error = mutableStateOf(false)
    val error get() = _error.value

    private val _privateGroupState = mutableStateOf(PrivateGroupState())
    val privateGroupState get() = _privateGroupState.value

    private val _showDialog = MutableSharedFlow<Boolean>()
    val showDialog = _showDialog.asSharedFlow()

    private val milestones = listOf(20000, 50000, 100000, 2000000, 400000, 700000)
    private val alertMilestones = mutableSetOf<Int>().apply {
        val completeMilestones = sp.getStringSet("alert_milestones", emptySet()) ?: emptySet()
        completeMilestones.mapTo(this) {it.toInt()}
    }

    private val _showMilestoneDialog = MutableSharedFlow<Boolean>()
    val showMilestoneDialog = _showMilestoneDialog.asSharedFlow()

    init {
        Log.d("HomeViewModel: ","HomeViewModel 실행")
        viewModelScope.launch {
            userInfo.value = getUserInfoUseCase()
        }
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

            // join이 되어 있지 않은 상황이라면 인원수 체크를 해서 다이얼로그 노출
            if (joined.not()) {
                details?.takeIf { it.currentPerson == it.maxPerson }?.let {
                    Log.d("HomeViewModel:", "ShowDialog")
                    _showDialog.emit(true)
                    return@collect
                }
            }

            _privateGroupState.value = _privateGroupState.value.copy(
                showPasswordSheet = !joined,
                isJoinedGroup = joined,
            )
        }
    }

    fun joinGroup(password: String) = viewModelScope.launch {
        val userInfo = getUserInfoUseCase()
        _joinResponseState.value = RequestState.Loading
        joinGroupUseCase(
            GroupJoinRequest(
                userId = (userInfo?.userId?.toInt() ?: -1),
                groupId = (selectedGroupId.value ?: -1),
                deviceToken = TokenManager.fcmToken,
                groupPassword = password,
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

    fun checkMileStone() {
        viewModelScope.launch {
            val userId = getUserInfoUseCase()?.userId ?: -1
            val totalScore = fetchProfileItemUseCase(userId.toString()).totalRankScore

            for (milestone in milestones) {
                if (totalScore >= milestone && !alertMilestones.contains(milestone)) {
                    _showMilestoneDialog.emit(true)
                    alertMilestones.add(milestone)
                    saveMileStone()
                }
            }
        }
    }

    private fun saveMileStone() {
        val completeMileStones = alertMilestones.map { it.toString() }.toSet()
        sp.edit().putStringSet("alert_milestones",completeMileStones).apply()
    }

    companion object {
        const val TAG = "HomeViewModel"

        const val MAIN = "main"
        const val POPULAR = "popular"
        const val LATEST = "latest"
    }
}