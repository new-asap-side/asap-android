package com.asap.domain.repository

import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.entity.remote.GroupDetails
import com.asap.domain.entity.remote.GroupRanking
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    // 오늘의 인기 그룹 리스트 조회
    suspend fun fetchTodayPopularGroup(): Flow<List<AlarmGroup>?>

    // 최신 그룹 리스트 조회
    suspend fun fetchLatestGroup(): Flow<List<AlarmGroup>?>

    // 그룹 상세 조회
    suspend fun fetchGroupDetails(groupId: Int): Flow<GroupDetails?>

    // 유저 알람 리스트 조회
    suspend fun fetchUserAlarmList(userId: Int): Flow<List<AlarmSummary>?>

    // 그룹 참여
    suspend fun postJoinGroup(body: Map<String, Any>): Flow<Boolean?>

    // 그룹 랭킹 조회
    suspend fun fetchGroupRanking(groupId: Int): Flow<List<GroupRanking>?>

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
    ): Int?
}