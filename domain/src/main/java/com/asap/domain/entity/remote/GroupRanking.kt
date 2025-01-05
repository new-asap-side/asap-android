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
    val rankScore: Int
)