package com.asap.data.remote.service

import com.asap.domain.entity.remote.AlarmGroup
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GroupService {
    @GET("/group/popular")
    suspend fun fetchTodayPopularGroup(): Response<List<AlarmGroup>>

    @POST("/group/join")
    suspend fun postJoinGroup(@Body body: Map<String, Any>): Response<Boolean>
}