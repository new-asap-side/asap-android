package com.asap.domain.entity.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * 요청 시 성공, 실패 여부만 반환
 * result: Boolean
 */
@JsonClass(generateAdapter = true)
data class WhetherResponse(
    @Json(name = "result")
    val result: Boolean
)

@JsonClass(generateAdapter = true)
data class RankingNumberResponse(
    @Json(name = "rank_number")
    val rankNumber: Int
)