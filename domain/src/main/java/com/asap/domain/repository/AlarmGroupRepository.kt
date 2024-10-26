package com.asap.domain.repository

import com.asap.domain.entity.AlarmGroup
import kotlinx.coroutines.flow.Flow

interface AlarmGroupRepository {
    suspend fun fetchGroupList(): Flow<List<AlarmGroup>>
}