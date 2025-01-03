package com.asap.data.remote.service

import com.asap.data.remote.request.PostGroupCreateRequest
import com.asap.data.remote.response.PostGroupCreateResponse
import com.asap.domain.entity.remote.Alarm
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.entity.remote.GroupDetails
import com.asap.domain.entity.remote.GroupJoinRequest
import com.asap.domain.entity.remote.GroupJoinResponse
import com.asap.domain.entity.remote.GroupRanking
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupService {
    @GET("/group/popular")
    suspend fun fetchTodayPopularGroup(): Response<List<AlarmGroup>>

    @GET("/group/latest")
    suspend fun fetchLatestGroup(): Response<List<AlarmGroup>>

    @GET("/group/{group_id}")
    suspend fun fetchGroupDetails(@Path("group_id") groupId: Int): Response<GroupDetails>

    // 유저 알람 리스트 조회
    @GET("/group/user/{user_id}")
    suspend fun fetchUserAlarmList(@Path("user_id") userId: Int): Response<List<AlarmSummary>>

    @GET("/group/ranking?")
    suspend fun fetchGroupRanking(@Query("groupId") groupId: Int): Response<List<GroupRanking>>

    @POST("/group/join")
    suspend fun postJoinGroup(@Body body: GroupJoinRequest): Response<GroupJoinResponse>

    @POST("/group/create")
    suspend fun postCreateGroup(@Body body: PostGroupCreateRequest): Response<PostGroupCreateResponse>
}