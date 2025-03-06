package com.asap.domain.repository

import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.entity.remote.GroupDetails
import com.asap.domain.entity.remote.GroupJoinRequest
import com.asap.domain.entity.remote.GroupJoinResponse
import com.asap.domain.entity.remote.GroupRanking
import com.asap.domain.entity.remote.RankingNumberResponse
import com.asap.domain.entity.remote.WhetherResponse
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    // 오늘의 인기 그룹 리스트 조회
    suspend fun fetchTodayPopularGroup(): Flow<List<AlarmGroup>?>

    // 최신 그룹 리스트 조회
    suspend fun fetchLatestGroup(): Flow<List<AlarmGroup>?>

    // 그룹 상세 조회
    suspend fun fetchGroupDetails(groupId: Int): Flow<GroupDetails?>

    // 그룹 검색
    suspend fun searchGroup(query: String): Flow<List<AlarmGroup>>

    // 유저 알람 리스트 조회
    suspend fun fetchUserAlarmList(userId: Int): Flow<List<AlarmSummary>?>

    // 그룹 참여
    suspend fun postJoinGroup(body: GroupJoinRequest): Flow<GroupJoinResponse?>

    // 그룹 탈퇴
    suspend fun withdrawGroup(userId: Int, groupId: Int): Flow<WhetherResponse?>

    // 그룹 랭킹 조회
    suspend fun fetchGroupRanking(groupId: Int): Flow<List<GroupRanking>?>

    // 그룹 랭킹 등수 조회
    suspend fun fetchRankingNumber(groupId: Int): Flow<RankingNumberResponse?>

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
    ): Int?

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
    )

    suspend fun postPersonalEdit(
        userId: Int,
        groupId: Int,
        alarmType: String,
        alarmVolume: Int?,
        musicTitle: String?
    )

    suspend fun postReportGroup(userId: Int, groupId: Int, survey: String)
}