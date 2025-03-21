package com.asap.data.remote.datasource

import com.asap.data.remote.request.PostGroupCreateRequest
import com.asap.data.remote.request.PostGroupEditRequest
import com.asap.data.remote.request.PostPersonalEditRequest
import com.asap.data.remote.request.PostReportGroupRequest
import com.asap.data.remote.response.PostGroupCreateResponse
import com.asap.data.remote.service.GroupService
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.entity.remote.GroupDetails
import com.asap.domain.entity.remote.GroupJoinRequest
import com.asap.domain.entity.remote.GroupJoinResponse
import com.asap.domain.entity.remote.RankingNumberResponse
import com.asap.domain.entity.remote.WhetherResponse
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

    suspend fun searchGroup(query: String): Flow<List<AlarmGroup>> = flow {
        groupService.searchGroup(query).also {
            if (!it.isSuccessful) throw HttpException(it)
            emit(it.body() ?: emptyList())
        }
    }

    suspend fun fetchGroupDetails(groupId: Int, userId: Int): Flow<GroupDetails?> = flow {
        val response = groupService.fetchGroupDetails(groupId = groupId, userId = userId)
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

    suspend fun withdrawGroup(userId: Int, groupId: Int): Flow<WhetherResponse?> = flow {
        groupService.withdrawGroup(
            mapOf("user_id" to userId, "group_id" to groupId)
        ).let { response ->
            if (!response.isSuccessful) throw HttpException(response)
            emit(response.body())
        }
    }

    suspend fun fetchMyRanking(userId: Int) = flow {
        groupService.fetchMyRankings(userId = userId).let { response ->
            if (!response.isSuccessful) throw HttpException(response)
            emit(response.body())
        }
    }

    suspend fun fetchGroupRanking(groupId: Int) = flow {
        groupService.fetchGroupRanking(groupId = groupId).let { response ->
            if(!response.isSuccessful) throw HttpException(response)
            emit(response.body())
        }
    }

    suspend fun fetchTodayRanking(groupId: Int) = flow {
        groupService.fetchTodayRanking(groupId = groupId).let { response ->
            if (!response.isSuccessful) throw HttpException(response)
            emit(response.body())
        }
    }

    suspend fun fetchRankingNumber(groupId: Int, userId: Int): Flow<RankingNumberResponse?> = flow {
        val response = groupService.fetchRankingNumber(groupId = groupId, userId = userId)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        emit(response.body())
    }

    suspend fun postCreateGroup(
        groupImage: String,
        alarmDays: List<String>,
        alarmEndDate: String,
        alarmTime: String,
        alarmType: String,
        alarmUnlockContents: String,
        alarmVolume: Float?,
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

    suspend fun postGroupEdit(
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
        return PostGroupEditRequest(
            userId = userId,
            groupId = groupId,
            title = title,
            description = description,
            maxPerson = maxPerson,
            alarmUnlockContents = alarmUnlockContents,
            isPublic = isPublic,
            groupPassword = groupPassword,
            groupImage = groupImage
        ).let { groupService.postGroupEdit(it) }
    }

    suspend fun postPersonalEdit(
        userId: Int,
        groupId: Int,
        alarmType: String,
        alarmVolume: Int?,
        musicTitle: String?
    ) {
        return PostPersonalEditRequest(
            userId = userId,
            groupId = groupId,
            alarmType = alarmType,
            alarmVolume = alarmVolume,
            musicTitle = musicTitle
        ).let { groupService.postPersonalEdit(it) }
    }

    suspend fun postReportGroup(
        userId: Int,
        groupId: Int,
        survey: String
    ) {
        PostReportGroupRequest(
            groupId = groupId,
            userId = userId,
            survey = survey
        ).let {
            groupService.postReportGroup(it)
        }
    }
}