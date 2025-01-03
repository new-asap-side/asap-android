package com.asap.data.remote.datasource

import com.asap.data.remote.request.PostGroupCreateRequest
import com.asap.data.remote.response.PostGroupCreateResponse
import com.asap.data.remote.service.GroupService
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.entity.remote.GroupDetails
import com.asap.domain.entity.remote.GroupJoinRequest
import com.asap.domain.entity.remote.GroupJoinResponse
import com.asap.domain.entity.remote.GroupRanking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GroupRemoteDataSource @Inject constructor(
    private val groupService: GroupService
) {
    suspend fun fetchTodayPopularGroup(): Flow<List<AlarmGroup>?> = flow {
        val response = groupService.fetchTodayPopularGroup()
        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        emit(response.body())
    }

    suspend fun fetchLatestGroup(): Flow<List<AlarmGroup>?> = flow {
        val response = groupService.fetchLatestGroup()
        if(!response.isSuccessful) {
            throw HttpException(response)
        }

        emit(response.body())
    }

    suspend fun fetchGroupDetails(groupId: Int): Flow<GroupDetails?> = flow {
        val response = groupService.fetchGroupDetails(groupId = groupId)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        emit(response.body())
    }

    suspend fun fetchUserAlarmList(userId: Int): Flow<List<AlarmSummary>?> = flow {
        val response = groupService.fetchUserAlarmList(userId = userId)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        emit(response.body())
    }

    suspend fun postJoinGroup(body: GroupJoinRequest): Flow<GroupJoinResponse?> = flow {
        val response = groupService.postJoinGroup(body)
        if(!response.isSuccessful) {
            throw HttpException(response)
        }

        emit(response.body())
    }

    suspend fun fetchGroupRanking(groupId: Int): Flow<List<GroupRanking>?> = flow {
        val response = groupService.fetchGroupRanking(groupId = groupId)
        emit(response.body())
    }

    suspend fun postCreateGroup(
        groupImage: String,
        alarmDays: List<String>,
        alarmEndDate: String,
        alarmTime: String,
        alarmType: String,
        alarmUnlockContents: String,
        alarmVolume: Int?,
        description: String,
        deviceToken: String,
        deviceType: String,
        groupPassword: String?,
        isPublic: Boolean,
        maxPerson: Int,
        musicTitle: String?,
        title: String,
        userId: Int
    ): PostGroupCreateResponse? {
        val groupCreateRequest = PostGroupCreateRequest(
            groupImage = groupImage,
            alarmDays = alarmDays,
            alarmEndDate = alarmEndDate,
            alarmTime = alarmTime,
            alarmType = alarmType,
            alarmUnlockContents = alarmUnlockContents,
            alarmVolume = alarmVolume,
            description = description,
            deviceToken = deviceToken,
            deviceType = deviceType,
            groupPassword = groupPassword,
            isPublic = isPublic,
            maxPerson = maxPerson,
            musicTitle = musicTitle,
            title = title,
            userId = userId
        )
        return groupService.postCreateGroup(groupCreateRequest).body()
    }
}