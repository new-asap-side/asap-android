package com.asap.data.remote.datasource

import com.asap.data.remote.service.GroupService
import com.asap.domain.entity.remote.AlarmGroup
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

    suspend fun postJoinGroup(body: Map<String, Any>): Flow<Boolean?> = flow {
        val response = groupService.postJoinGroup(body)
        emit(response.body())
    }

    suspend fun fetchGroupRanking(groupId: Int): Flow<List<GroupRanking>?> = flow {
        val response = groupService.fetchGroupRanking(groupId = groupId)
        emit(response.body())
    }
}