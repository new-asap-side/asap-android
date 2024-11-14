package com.asap.domain.repository

import com.asap.domain.entity.remote.AlarmGroup
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    // 오늘의 인기 그룹 리스트 조회
    suspend fun fetchTodayPopularGroup(): Flow<List<AlarmGroup>?>

    // 그룹 참여
    suspend fun postJoinGroup(body: Map<String, Any>): Flow<Boolean?>
}