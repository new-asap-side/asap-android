package com.asap.data.remote.datasource

import com.asap.data.remote.service.GroupService
import com.asap.domain.entity.remote.AlarmGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GroupRemoteDataSource @Inject constructor(
    private val groupService: GroupService
) {
    suspend fun fetchTodayPopularGroup(): Flow<List<AlarmGroup>?> = flow {
        val response = groupService.fetchTodayPopularGroup()
        emit(response.body())
    }
}