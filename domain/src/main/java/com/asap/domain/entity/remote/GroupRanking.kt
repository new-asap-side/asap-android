package com.asap.domain.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class GroupRanking(
    @Json(name = "thumbnail")
    val thumbnail: String,
    @Json(name = "nickname")
    val nickname: String,
    @Json(name = "score")
    val score: Int
)

fun GroupRanking.dummy(): GroupRanking =
    GroupRanking(
        thumbnail = "",
        nickname = "James",
        score = 0
    )
