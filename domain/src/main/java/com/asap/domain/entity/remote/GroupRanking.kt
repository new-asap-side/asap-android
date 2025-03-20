package com.asap.domain.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class GroupRanking(
    @Json(name = "nickName")
    val nickName: String,
    @Json(name = "profileImgUrl")
    val thumbnail: String,
    @Json(name = "rankNumber")
    val rankNumber: Int,
    @Json(name = "rankScore")
    val rankScore: Int,
    @Json(name = "created_at")
    val createdAt: String
)

@JsonClass(generateAdapter = true)
data class MyRankingEntity(
    @Json(name = "title")
    val title: String,
    @Json(name = "rank_number")
    val rankNumber: Int
)