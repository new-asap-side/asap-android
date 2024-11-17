package com.asap.data.repository

import com.asap.data.remote.datasource.GroupRemoteDataSource
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val remoteDataSource: GroupRemoteDataSource
): GroupRepository {
    override suspend fun fetchTodayPopularGroup(): Flow<List<AlarmGroup>?> {
        return remoteDataSource.fetchTodayPopularGroup()
    }

    override suspend fun postJoinGroup(body: Map<String, Any>): Flow<Boolean?> {
        return remoteDataSource.postJoinGroup(body = body)
    }
}