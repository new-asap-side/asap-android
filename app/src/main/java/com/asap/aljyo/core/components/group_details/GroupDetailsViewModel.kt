package com.asap.aljyo.core.components.group_details

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.asap.aljyo.core.components.edit.GroupEditState
import com.asap.aljyo.core.components.edit.PersonalEditState
import com.asap.aljyo.ui.UiState
import com.asap.data.utility.DateTimeManager
import com.asap.data.utility.DateTimeManager.sortByDay
import com.asap.domain.entity.remote.GroupDetails
import com.asap.domain.entity.remote.GroupJoinRequest
import com.asap.domain.entity.remote.GroupMember
import com.asap.domain.entity.remote.UserGroupType
import com.asap.domain.entity.remote.auth.TokenManager
import com.asap.domain.usecase.group.FetchGroupDetailsUseCase
import com.asap.domain.usecase.group.JoinGroupUseCase
import com.asap.domain.usecase.group.WithdrawGroupUseCase
import com.asap.domain.usecase.user.GetUserInfoUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException

class GroupDetailsViewModel @AssistedInject constructor(
    @Assisted private val groupId: Int,
    private val fetchGroupDetailsUseCase: FetchGroupDetailsUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val joinGroupUseCase: JoinGroupUseCase,
    private val withdrawGroupUseCase: WithdrawGroupUseCase,
) : ViewModel() {
    private val _groupDetailsState = MutableStateFlow<UiState<GroupDetails?>>(UiState.Loading)
    val groupDetails get() = _groupDetailsState.asStateFlow()

    private val _userGroupType = mutableStateOf<UserGroupType?>(null)
    val userGroupType get() = _userGroupType.value

    private val _privateSettingState = MutableStateFlow<GroupMember?>(null)
    val privateSettingState get() = _privateSettingState.asStateFlow()

    private val _groupEdit = MutableSharedFlow<GroupEditState>()
    val groupEdit = _groupEdit.asSharedFlow()

    private val _personalEdit = MutableSharedFlow<PersonalEditState>()
    val personalEdit = _personalEdit.asSharedFlow()

    private val _complete = MutableSharedFlow<Unit>()
    val complete = _complete.asSharedFlow()

    private val _withdrawState = MutableStateFlow(false)
    val withdrawState get() = _withdrawState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _checkNew = MutableStateFlow(true)
    val checkNew = _checkNew.asStateFlow()

    fun fetchGroupDetails(internal: Boolean = false) {
        viewModelScope.launch {
            if (!internal) {
                _groupDetailsState.value = UiState.Loading
            } else {
                _isLoading.value = true
            }

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

                val mId = getUserInfoUseCase()?.userId?.toInt()
                result?.users.also { participateUsers ->
                    val target = participateUsers?.find { participants ->
                        mId == participants.userId
                    }

                    // 그룹장 / 참여자 / 미 참여자 구분
                    if (target == null) {
                        _userGroupType.value = UserGroupType.NonParticipant
                    } else {
                        // 개인 설정
                        _privateSettingState.value = target
                        _userGroupType.value = if (target.isGroupMaster) {
                            UserGroupType.Leader
                        } else {
                            UserGroupType.Participant
                        }
                    }
                }
                _groupDetailsState.value = UiState.Success(result)
                _isLoading.value = false
            }
        }
    }

    fun findLeader(groupDetails: GroupDetails?): GroupMember? {
        return try {
            groupDetails?.users?.first { user ->
                user.isGroupMaster
            }
        } catch (e: NoSuchElementException) {
            null
        }
    }

    fun parseISOFormat(stringDate: String): String = DateTimeManager.parseISO(stringDate)

    fun parseToAmPm(time: String): String = DateTimeManager.parseToAmPm(time)

    fun parseAlarmDays(groupDetails: GroupDetails?): String {
        val raw = groupDetails?.alarmDays ?: emptyList()
        return raw.sortByDay().joinToString(separator = " ")
    }

    fun navigateToGroupEdit() {
        (_groupDetailsState.value as UiState.Success).data?.let {
            GroupEditState(
                groupId = groupId,
                alarmUnlockContents = it.alarmUnlockContents,
                groupImage = it.groupThumbnailImageUrl,
                title = it.title,
                description = it.description,
                currentPerson = it.currentPerson,
                isPublic = it.isPublic,
                groupPassword = null
            )
        }?.also {
            viewModelScope.launch {
                _groupEdit.emit(it)
            }
        }
    }

    fun navigateToPersonalEdit() {
        val memberList = (_groupDetailsState.value as UiState.Success).data?.users

        viewModelScope.launch {
            val userId = getUserInfoUseCase()?.userId?.toInt()

            memberList?.find { it.userId == userId }?.let {
                PersonalEditState(
                    alarmType = it.alarmType,
                    musicTitle = it.musicTitle,
                    alarmVolume = it.volume.toFloat()
                ).also { personalEditState ->
                    _personalEdit.emit(personalEditState)
                }
            }
        }
    }

    fun joinGroup() {
        viewModelScope.launch {
            _isLoading.value = true
            val userInfo = getUserInfoUseCase()

            joinGroupUseCase(
                GroupJoinRequest(
                    userId = userInfo?.userId?.toInt() ?: -1,
                    groupId = groupId,
                    deviceToken = TokenManager.fcmToken,
                    groupPassword = null,
                )
            ).catch { e ->
                Log.d("GroupDetailsViewModel","joinGroup: $e")
            }.collect {
                _isLoading.value = false
                _complete.emit(Unit)
            }
        }
    }

    fun withdrawGroup() = viewModelScope.launch {
        _isLoading.value = true
        val userInfo = getUserInfoUseCase()

        withdrawGroupUseCase(
            userId = userInfo?.userId?.toInt() ?: -1,
            groupId = groupId
        ).catch {
            _isLoading.value = false
        }.collect { response ->
            _withdrawState.value = response?.result ?: false
            _isLoading.value = false
        }
    }

    fun checkJoinGroup(): Boolean {
        val groupState = (_groupDetailsState.value as? UiState.Success)?.data

        return if (groupState != null) {
            groupState.maxPerson > groupState.currentPerson
        } else {
            false
        }
    }

    fun toggleCheckNew() {
        _checkNew.value = false
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