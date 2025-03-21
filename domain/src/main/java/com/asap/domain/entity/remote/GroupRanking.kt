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
    val createdAt: String,
    @Json(name = "itemName")
    val profileItem: String?,
)

@JsonClass(generateAdapter = true)
data class MyRanking(
    @Json(name = "groupId")
    val groupId: Int = 1,
    @Json(name = "title")
    val title: String,
    @Json(name = "rank_number")
    val rankNumber: Int
)