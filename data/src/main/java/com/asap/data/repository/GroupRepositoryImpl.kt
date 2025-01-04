package com.asap.data.repository

import com.asap.data.local.AppDatabase
import com.asap.data.remote.datasource.GroupRemoteDataSource
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.entity.remote.GroupDetails
import com.asap.domain.entity.remote.GroupJoinRequest
import com.asap.domain.entity.remote.GroupJoinResponse
import com.asap.domain.entity.remote.GroupRanking
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.math.max

class GroupRepositoryImpl @Inject constructor(
    private val remoteDataSource: GroupRemoteDataSource,
    private val localDataSource: AppDatabase,
): GroupRepository {
    override suspend fun fetchTodayPopularGroup(): Flow<List<AlarmGroup>?> {
        return remoteDataSource.fetchTodayPopularGroup()
    }

    override suspend fun fetchLatestGroup(): Flow<List<AlarmGroup>?> {
        return remoteDataSource.fetchLatestGroup()
    }

    override suspend fun fetchGroupDetails(groupId: Int): Flow<GroupDetails?> {
        val userDao = localDataSource.userDao()
        val users = userDao.selectAll()

        if (users.isEmpty()) throw NoSuchElementException("can't find user data")

        val user = users.first()
        return remoteDataSource.fetchGroupDetails(groupId = groupId, userId = user.userId.toInt())
    }

    override suspend fun fetchUserAlarmList(userId: Int): Flow<List<AlarmSummary>?> {
        return remoteDataSource.fetchUserAlarmList(userId = userId)
    }

    override suspend fun postJoinGroup(body: GroupJoinRequest): Flow<GroupJoinResponse?> {
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

    override suspend fun postGroupEdit(
        userId: Int,
        groupId: Int,
        title: String,
        description: String,
        maxPerson: Int,
        alarmUnlockContents: String,
        isPublic: Boolean,
        groupPassword: String?,
        groupImage: String
    ) {
        return remoteDataSource.postGroupEdit(
            userId = userId,
            groupId = groupId,
            title = title,
            description = description,
            maxPerson = maxPerson,
            alarmUnlockContents = alarmUnlockContents,
            isPublic = isPublic,
            groupPassword = groupPassword,
            groupImage = groupImage
        )
    }

    override suspend fun postPersonalEdit(
        userId: Int,
        groupId: Int,
        alarmType: String,
        alarmVolume: Int?,
        musicTitle: String?
    ) {
        return remoteDataSource.postPersonalEdit(
            userId = userId,
            groupId = groupId,
            alarmType = alarmType,
            alarmVolume = alarmVolume,
            musicTitle = musicTitle
        )
    }
}