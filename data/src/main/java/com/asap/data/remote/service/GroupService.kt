package com.asap.data.remote.service

import com.asap.domain.entity.remote.AlarmGroup
import retrofit2.Response
import retrofit2.http.GET

interface GroupService {
    @GET("/group/popular")
    suspend fun fetchTodayPopularGroup(): Response<List<AlarmGroup>>
}