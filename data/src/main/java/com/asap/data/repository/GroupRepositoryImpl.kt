package com.asap.data.repository

import com.asap.data.remote.datasource.GroupRemoteDataSource
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.entity.remote.GroupDetails
import com.asap.domain.entity.remote.GroupRanking
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val remoteDataSource: GroupRemoteDataSource
): GroupRepository {
    override suspend fun fetchTodayPopularGroup(): Flow<List<AlarmGroup>?> {
        return remoteDataSource.fetchTodayPopularGroup()
    }

    override suspend fun fetchLatestGroup(): Flow<List<AlarmGroup>?> {
        return remoteDataSource.fetchTodayPopularGroup()
    }

    override suspend fun fetchGroupDetails(groupId: Int): Flow<GroupDetails?> {
        return remoteDataSource.fetchGroupDetails(groupId = groupId)
    }

    override suspend fun fetchUserAlarmList(userId: Int): Flow<List<AlarmSummary>?> {
        return remoteDataSource.fetchUserAlarmList(userId = userId)
    }

    override suspend fun postJoinGroup(body: Map<String, Any>): Flow<Boolean?> {
        return remoteDataSource.postJoinGroup(body = body)
    }

    override suspend fun fetchGroupRanking(groupId: Int): Flow<List<GroupRanking>?> {
        return remoteDataSource.fetchGroupRanking(groupId = groupId)
    }

    override suspend fun postCreateGroup(
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
    ): Int? {
        return remoteDataSource.postCreateGroup(
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
        )?.groupId
    }
}