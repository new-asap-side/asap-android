package com.asap.data.remote.datasource

import com.asap.data.remote.service.AlarmService
import com.asap.domain.entity.remote.AlarmOffRequestBody
import com.asap.domain.entity.remote.WhetherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AlarmRemoteDataSource @Inject constructor(
    private val service: AlarmService
) {
    suspend fun release(userId: Int, groupId: Int): Flow<WhetherResponse?> = flow {
        val response = service.release(
            AlarmOffRequestBody(userId = userId, groupId = groupId)
        )

        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        emit(response.body())
    }
}