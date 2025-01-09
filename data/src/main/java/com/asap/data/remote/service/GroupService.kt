package com.asap.data.remote.service

import com.asap.data.remote.request.PostGroupCreateRequest
import com.asap.data.remote.request.PostGroupEditRequest
import com.asap.data.remote.request.PostPersonalEditRequest
import com.asap.data.remote.response.PostGroupCreateResponse
import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.entity.remote.GroupDetails
import com.asap.domain.entity.remote.GroupJoinRequest
import com.asap.domain.entity.remote.GroupJoinResponse
import com.asap.domain.entity.remote.GroupRanking
import com.asap.domain.entity.remote.RankingNumberResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupService {
    // 인기 그룹 조회
    @GET("/group/popular")
    suspend fun fetchTodayPopularGroup(): Response<List<AlarmGroup>>

    // 최신 그룹 조회
    @GET("/group/latest")
    suspend fun fetchLatestGroup(): Response<List<AlarmGroup>>

    // 그룹 상세 조회
    @GET("/group/{group_id}/{user_id}")
    suspend fun fetchGroupDetails(
        @Path("group_id") groupId: Int,
        @Path("user_id") userId: Int
    ): Response<GroupDetails>

    // 유저 알람 리스트 조회
    @GET("/group/user/{user_id}")
    suspend fun fetchUserAlarmList(@Path("user_id") userId: Int): Response<List<AlarmSummary>>

    // 그룹 랭킹
    @GET("/group/ranking/{group_id}")
    suspend fun fetchGroupRanking(@Query("group_id") groupId: Int): Response<List<GroupRanking>>

    // 랭킹 등수 조회
    @GET("/group/ranking/{group_id}/{user_id}")
    suspend fun fetchRankingNumber(
        @Path("group_id") groupId: Int,
        @Path("user_id") userId: Int
    ): Response<RankingNumberResponse>

    // 그룹 참여
    @POST("/group/join")
    suspend fun postJoinGroup(@Body body: GroupJoinRequest): Response<GroupJoinResponse>

    // 그룹 생성
    @POST("/group/create")
    suspend fun postCreateGroup(@Body body: PostGroupCreateRequest): Response<PostGroupCreateResponse>

    @POST("/group/edit")
    suspend fun postGroupEdit(@Body body: PostGroupEditRequest): Response<Unit>

    @POST("/group/edit-personal")
    suspend fun postPersonalEdit(@Body body: PostPersonalEditRequest): Response<Unit>
}