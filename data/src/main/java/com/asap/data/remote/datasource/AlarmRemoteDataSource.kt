package com.asap.data.remote.datasource

import com.asap.data.remote.request.PatchAlarmTokenBody
import com.asap.data.remote.service.AlarmService
import com.asap.domain.entity.remote.AlarmOffRequestBody
import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.entity.remote.alarm.AlarmOffRate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AlarmRemoteDataSource @Inject constructor(
    private val alarmService: AlarmService
) {
    suspend fun patchAlarmToken(userId: Int, token: String): WhetherResponse?  {
        return PatchAlarmTokenBody(userId = userId, alarmToken = token).let { body ->
            alarmService.patchAlarmToken(body)
        }.run {
            if (!isSuccessful) throw HttpException(this)
            body().also(::println)
        }
    }

    suspend fun fetchAlarmOffRate(userId: String): Flow<AlarmOffRate?> = flow {
        val response = alarmService.fetchAlarmOffRate(userId = userId)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
        emit(response.body())
    }

    suspend fun release(userId: Int, groupId: Int): Flow<WhetherResponse?> = flow {
        val response = alarmService.release(
            AlarmOffRequestBody(userId = userId, groupId = groupId)
        )

        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        emit(response.body())
    }
}