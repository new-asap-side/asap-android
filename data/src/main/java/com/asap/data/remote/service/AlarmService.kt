package com.asap.data.remote.service

import com.asap.domain.entity.remote.AlarmOffRequestBody
import com.asap.domain.entity.remote.WhetherResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AlarmService {
    // 알람 해제
    @POST("/alarm/off")
    suspend fun release(@Body boy: AlarmOffRequestBody): Response<WhetherResponse?>
}