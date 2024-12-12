package com.asap.data.remote.datasource

import com.asap.data.remote.request.PostGroupCreateRequest
import com.asap.data.remote.response.PostGroupCreateResponse
import com.asap.data.remote.service.GroupService
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.entity.remote.GroupRanking
import com.squareup.moshi.Json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.max

class GroupRemoteDataSource @Inject constructor(
    private val groupService: GroupService
) {
    suspend fun fetchTodayPopularGroup(): Flow<List<AlarmGroup>?> = flow {
        val response = groupService.fetchTodayPopularGroup()
        emit(response.body())
    }

    suspend fun postJoinGroup(body: Map<String, Any>): Flow<Boolean?> = flow {
        val response = groupService.postJoinGroup(body)
        emit(response.body())
    }

    suspend fun fetchGroupRanking(groupId: Int): Flow<List<GroupRanking>?> = flow {
        val response = groupService.fetchGroupRanking(groupId = groupId)
        emit(response.body())
    }

    suspend fun postCreateGroup(
        alarmDay: String,
        alarmEndDate: String,
        alarmTime: String,
        alarmType: String,
        alarmUnlockContents: String,
        alarmVolume: Int,
        description: String,
        deviceToken: String,
        deviceType: String,
        groupPassword: String,
        isPublic: Boolean,
        maxPerson: Int,
        musicTitle: String,
        title: String,
        userId: Int
    ): PostGroupCreateResponse? {
        val groupCreateRequest = PostGroupCreateRequest(
            alarmDay = alarmDay,
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