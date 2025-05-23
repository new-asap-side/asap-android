package com.asap.data.remote.service

import com.asap.data.remote.request.PatchAlarmTokenBody
import com.asap.domain.entity.remote.AlarmOffRequestBody
import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.entity.remote.alarm.AlarmOffRate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AlarmService {
    // FCM token 동기화
    @PATCH("/admin/alarm_token")
    suspend fun patchAlarmToken(@Body body: PatchAlarmTokenBody): Response<WhetherResponse>

    @GET("/alarm/off-rate/{user_id}")
    suspend fun fetchAlarmOffRate(@Path("user_id") userId: String): Response<AlarmOffRate?>

    // 알람 해제
    @POST("/alarm/off")
    suspend fun release(@Body boy: AlarmOffRequestBody): Response<WhetherResponse?>
}