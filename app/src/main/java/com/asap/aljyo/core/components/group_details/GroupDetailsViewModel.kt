package com.asap.aljyo.core.components.group_details

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.ui.RequestState
import com.asap.aljyo.ui.UiState
import com.asap.data.utility.DateTimeManager
import com.asap.domain.entity.remote.GroupDetails
import com.asap.domain.entity.remote.GroupMember
import com.asap.domain.entity.remote.UserGroupType
import com.asap.domain.usecase.group.FetchGroupDetailsUseCase
import com.asap.domain.usecase.group.JoinGroupUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.LinkedList
import java.util.Queue

class GroupDetailsViewModel @AssistedInject constructor(
    private val fetchGroupDetailsUseCase: FetchGroupDetailsUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val joinGroupUseCase: JoinGroupUseCase,
    @Assisted private val groupId: Int
) : ViewModel() {
    private val _groupDetailsState = MutableStateFlow<UiState<GroupDetails?>>(UiState.Loading)
    val groupDetails get() = _groupDetailsState.asStateFlow()

    private val _userGroupType = mutableStateOf<UserGroupType?>(null)
    val userGroupType get() = _userGroupType.value

    private var active = true

    private val _fastestAlarmQueue: Queue<String> = LinkedList()
    private val _nextAlarmTimeFlow: Flow<String> = flow {
        val nextAlarmTime = _fastestAlarmQueue.peek()
        if (nextAlarmTime != null) {
            emit(nextAlarmTime)
        }
    }

    private val _nextAlarmTime = MutableStateFlow("")
    val nextAlarmTime = _nextAlarmTime.asStateFlow()

    private val _joinGroupState = MutableStateFlow<RequestState<Boolean>>(RequestState.Initial)
    val joinGroupState get() = _joinGroupState

    init {
        viewModelScope.launch {
            delay(500)
            fetchGroupDetailsUseCase.invoke(groupId = groupId).catch { e ->
                Log.e(TAG, "error: $e")
                val errorCode = when (e) {
                    is HttpException -> e.code()
                    else -> -1
                }
                _groupDetailsState.value = UiState.Error(errorCode)
            }.collect { result ->
                Log.d(TAG, "$result")

                participationStatus(result)
                sortedByFastest(
                    result?.alarmDays?.map { day ->
                        "$day ${result.alarmTime}"
                    } ?: listOf()
                )

                observingRemainTime()

                _groupDetailsState.value = UiState.Success(result)
            }
        }
    }

    // 그룹 참여 여부 확인
    private fun participationStatus(groupDetails: GroupDetails?) = viewModelScope.launch {
        groupDetails?.users.also { participateUsers ->
            val target = participateUsers?.find { participants ->
                getUserInfoUseCase.invoke().userId.let { myUserId ->
                    myUserId.toInt() == participants.userId
                }
            }

            if (target == null) {
                _userGroupType.value = UserGroupType.NonParticipant
                return@launch
            }

            _userGroupType.value = if (target.isGroupMaster) {
                UserGroupType.Leader
            } else {
                UserGroupType.Participant
            }

        }
    }

    private fun sortedByFastest(dates: List<String>) {
        dates.sortedBy {
            DateTimeManager.diffFromNow(it)
        }.also { list ->
            _fastestAlarmQueue.addAll(list)
        }
    }

    private fun observingRemainTime() = viewModelScope.launch {
        while (active) {
            delay(1000)
            _nextAlarmTimeFlow.collect { nextAlarmDate ->
                val duration = DateTimeManager.diffFromNow(nextAlarmDate, basedMinite = false)
                _nextAlarmTime.value = DateTimeManager.parseToDayBySecond(duration)
            }
        }
    }

    fun findLeader(groupDetails: GroupDetails?): GroupMember? {
        return groupDetails?.users?.first { user ->
            user.isGroupMaster
        }
    }

    fun parseISOFormat(stringDate: String): String = DateTimeManager.parseISO(stringDate)

    fun parseToAmPm(time: String): String = DateTimeManager.parseToAmPm(time)

    fun joinGroup(body: Map<String, Any>) = viewModelScope.launch {
        joinGroupUseCase.invoke(body).catch { e ->
            // error
            _joinGroupState.value = RequestState.Error(errorCode = e.toString())
        }.collect { result ->
            if (result != null) {
                _joinGroupState.value = RequestState.Success(result)
            }
        }
    }

    override fun onCleared() {
        active = false
        super.onCleared()
    }

    @AssistedFactory
    interface GroupDetailsViewModelFactory {
        fun create(groupId: Int): GroupDetailsViewModel
    }

    companion object {
        private const val TAG = "GroupDetailsViewModel"

        @Suppress("UNCHECKED_CAST")
        fun provideGroupDetailsViewModelFactory(
            factory: GroupDetailsViewModelFactory,
            groupId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                factory.create(groupId = groupId) as T
        }
    }
}